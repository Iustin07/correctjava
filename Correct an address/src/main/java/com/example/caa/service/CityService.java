package com.example.caa.service;

import com.example.caa.model.*;
import com.example.caa.repository.CityRepository;
import com.example.caa.utils.*;
import com.example.caa.utils.geo.info.*;
import com.example.caa.utils.geo.info.CityInfo;
import com.example.caa.utils.geo.api.*;
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
import java.util.Random;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City getCity(Integer id) {
        return this.cityRepository.findById(id).get();
    }

    public List<City> getCities(String name) {
        return this.cityRepository.findByCityName(name);
    }

    public City getRandomCityInState(State state) {
        List<City> cities = cityRepository.findAllByState(state);
        Random random = new Random();
        return cities.get(random.nextInt(cities.size()));
    }

    public List<City> getCitiesStartingWith(String prefix) {
        List<City> cities = this.cityRepository.findByCityNameIsStartingWith(prefix);
        return cities;
    }
    
    public List<City> getAllByState(State state){
        return this.cityRepository.findAllByState(state);
    }

    public List<City> getAllByStateAndNameStartingWith(State state, String prefix){
        return this.cityRepository.findAllByStateAndCityNameStartingWith(state, prefix);
    }

    public ResponseEntity<Object> addCity(City city) {
        if (city == null) {
            return new ResponseEntity<>("Cannot add an empty city!", HttpStatus.BAD_REQUEST);
        }
        this.cityRepository.save(city);
        return new ResponseEntity<>("City added successfully!", HttpStatus.OK);

    }

    public ResponseEntity<Object> deleteCity(City city) {
        if (city == null) {
            return new ResponseEntity<>("Cannot delete an empty city!", HttpStatus.BAD_REQUEST);
        }
        this.cityRepository.delete(city);
        return new ResponseEntity<>("City deleted successfully!", HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteCityById(Integer id) {
        if (id == null) {
            return new ResponseEntity<>("Cannot delete an empty city!", HttpStatus.BAD_REQUEST);
        }
        this.cityRepository.delete(getCity(id));
        return new ResponseEntity<>("City deleted successfully!", HttpStatus.OK);

    }

    public ResponseEntity<Object> addAllCitiesToDB() {
        HttResponse ht = new HttResponse(GeoDbRequest.host1, GeoDbRequest.host1);
        JsonElement je = ht.getElement();
        CountriesApi countryApi = new CountriesApi();
        countryApi.setJe(je);
        System.out.println("in here!!");

        List<Country> countries = countryApi.getNewCountries();
        System.out.println("got countries");

        Logger logger = LoggerFactory.getLogger(CityService.class);
        String uriCountry = "http://localhost:9192/countries/getById" + "?id=";
        String uriState = "http://localhost:9192/states/getById" + "?id=";
        logger.info("Start");
        RestTemplate restTemplate = new RestTemplate();

        CityApi cityApi = new CityApi(countries);
        List<CityInfo> newCities = cityApi.getNewCities();

        for (CityInfo cityInfo : newCities) {
            City city = new City();
            city.setCityId(cityInfo.getId());
            city.setCityName(cityInfo.getName());

            String uriCopyCountry = uriCountry + cityInfo.getCountry_id();
            String uriCopyState = uriState + cityInfo.getState_id();

            ResponseEntity<Country> responseEntityCountry = restTemplate.exchange(
                    uriCopyCountry, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            ResponseEntity<State> responseEntityState = restTemplate.exchange(
                    uriCopyState, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            city.setCountry(responseEntityCountry.getBody());
            city.setState(responseEntityState.getBody());
            city.setCountryCodeIso2(responseEntityCountry.getBody().getCountryCodeIso2());
            city.setStateCodeIso2(responseEntityState.getBody().getStateCodeIso2());

            addCity(city);

        }
        logger.info("Stop");
        return new ResponseEntity<>("Cities added successfully!", HttpStatus.CREATED);
    }
}
