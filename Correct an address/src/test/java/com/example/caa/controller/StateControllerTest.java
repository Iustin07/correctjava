package com.example.caa.controller;

import com.example.caa.model.Country;
import com.example.caa.model.State;
import com.example.caa.service.StateService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StateController.class)
public class StateControllerTest {

    @MockBean
    private StateService stateService;

    @Autowired
    private MockMvc mvc;

    @Test
    void getStateById() throws Exception {
        State state = new State();
        state.setStateId(4916);
        state.setStateName("Sibiu");
        state.setCountryCodeIso2("RO");
        
        Country country = new Country();
        country.setCountryId((short) 181);
        country.setCountryName("Romania");
        country.setCountryCodeIso2("RO");

        state.setCountry(country);

        Mockito.when(stateService.getState(4916)).thenReturn(state);

        mvc.perform(get("/states/getById?id=4916")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stateName", Matchers.is("Sibiu")))
                .andExpect(jsonPath("$.country.countryId", Matchers.is(country.getCountryId().intValue())))
                .andExpect(jsonPath("$.country.countryName", Matchers.is(country.getCountryName())))
                .andExpect(jsonPath("$.country.countryCodeIso2", Matchers.is(country.getCountryCodeIso2())))
        ;
    }
}