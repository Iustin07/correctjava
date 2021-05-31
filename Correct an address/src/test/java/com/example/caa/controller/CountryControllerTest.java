package com.example.caa.controller;

import com.example.caa.model.Country;
import com.example.caa.service.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CountryController.class)
class CountryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CountryService countryServiceMock;

    @Deprecated
    @Test
    void getCountryByName() throws Exception {
        Country country = new Country((short) 181, "Romania", "RO");
        when(countryServiceMock.getCountry("Romania")).thenReturn(country);
        mvc.perform(get("/countries/getByName?name=Romania")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.countryId").value(181))
                .andExpect(jsonPath("$.countryName").value("Romania"))
                .andExpect(jsonPath("$.countryCodeIso2").value("RO"));
    }

    @Test
    void getCountryById() throws Exception {
        Country country = new Country((short) 75, "France", "FR");
        when(countryServiceMock.getCountry((short) 75)).thenReturn(country);
        mvc.perform(get("http://localhost:9192/countries/getById?id=75")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.countryId").value(75))
                .andExpect(jsonPath("$.countryName").value("France"))
                .andExpect(jsonPath("$.countryCodeIso2").value("FR"));
    }

}