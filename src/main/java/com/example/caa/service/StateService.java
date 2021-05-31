package com.example.caa.service;


import com.example.caa.model.Country;
import com.example.caa.model.State;
import com.example.caa.repository.StateRepository;
import com.example.caa.utils.GeoDbRequest;
import com.example.caa.utils.*;
import com.example.caa.utils.geo.api.StatesApi;
import com.example.caa.utils.geo.info.*;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State getState(Integer id) {
        State state = this.stateRepository.findById(id).get();
        return state;
    }

    public List<State> getStates(String name) {
        List<State> states = this.stateRepository.findByStateName(name);
        return states;
    }

    public ResponseEntity<String> addState(State state) {
        if (state == null) {
            return new ResponseEntity<>("Cannot add an empty state!", HttpStatus.BAD_REQUEST);
        }
        this.stateRepository.save(state);
        return new ResponseEntity<>("State added successfully!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteState(State state) {
        if (state == null) {
            return new ResponseEntity<>("Cannot delete an empty state!", HttpStatus.BAD_REQUEST);
        }
        this.stateRepository.delete(state);
        return new ResponseEntity<>("State deleted successfully!", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteStateById(Integer id) {
        if (id == null) {
            return new ResponseEntity<>("Cannot delete an empty state!", HttpStatus.BAD_REQUEST);
        }
        this.stateRepository.delete(getState(id));
        return new ResponseEntity<>("State deleted successfully!", HttpStatus.OK);

    }

    public ResponseEntity<String> addAllStatesToDB() {
        HttResponse ht = new HttResponse(GeoDbRequest.host2, GeoDbRequest.host2);
        JsonElement je = ht.getElement();
        StatesApi statesApi = new StatesApi();
        statesApi.setJe(je);

        Logger logger = LoggerFactory.getLogger(StateService.class);
        String uri = "http://localhost:9192/countries/getById" + "?id=";
        logger.info("Start");
        RestTemplate restTemplate = new RestTemplate();

        List<StateInfo> newStates = statesApi.getNewStates();
        for (StateInfo stateInfo : newStates) {
            State state = new State();
            state.setStateId(stateInfo.getId());
            state.setStateName(stateInfo.getName());
            state.setStateCodeIso2(stateInfo.getIso2());

            String uriCopy = uri + stateInfo.getCountry_id();

            ResponseEntity<Country> responseEntity = restTemplate.exchange(
                    uriCopy, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            state.setCountry(responseEntity.getBody());
            state.setCountryCodeIso2(responseEntity.getBody().getCountryCodeIso2());

            addState(state);
        }
        logger.info("Stop");
        return new ResponseEntity<>("States added successfully!", HttpStatus.CREATED);
    }
}
