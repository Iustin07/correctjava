package com.example.caa.controller;

import com.example.caa.model.City;
import com.example.caa.model.Country;
import com.example.caa.model.State;
import com.example.caa.service.CityService;
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
@WebMvcTest(CityController.class)
class CityControllerTest {
    @MockBean
    private CityService cityService;

    @Autowired
    private MockMvc mvc;

    @Test
    void getCityById() throws Exception {
        City city = new City();
        city.setCityId(146434);
        city.setCityName("Sibiu");

        State state = new State();
        state.setStateId(4916);
        state.setStateName("Sibiu");
        state.setCountryCodeIso2("RO");

        Country country = new Country();
        country.setCountryId((short) 181);
        country.setCountryName("Romania");
        country.setCountryCodeIso2("RO");

        //state.setCountry(country);
        city.setCountry(country);
        city.setState(state);
        Mockito.when(cityService.getCity(146434)).thenReturn(city);

        mvc.perform(get("/cities/getById?id=146434")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.country.countryId", Matchers.is(country.getCountryId().intValue())))
                .andExpect(jsonPath("$.country.countryName", Matchers.is(country.getCountryName())))
                .andExpect(jsonPath("$.country.countryCodeIso2", Matchers.is(country.getCountryCodeIso2())))
                .andExpect(jsonPath("$.state.stateId", Matchers.is(state.getStateId())))
                .andExpect(jsonPath("$.state.stateName", Matchers.is(state.getStateName())))
                .andExpect(jsonPath("$.cityName").value("Sibiu"));
    }

}