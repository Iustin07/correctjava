package com.example.caa.controller;


import com.example.caa.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
//@SpringBootTest(AddressController.class)
@AutoConfigureMockMvc
class AddressControllerTestIntegration {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private AddressService service;

    @Test
    void getCorrectedAddress() throws Exception {
        //service.correctAddress(new PostalAddress("France","Iasi","Iasi"));
        String jSonBody = "{\n \"countryName\":\"France\",\n \"stateName\":\"Iasi\",\n  \"cityName\":\"Paris\" }";
        mvc.perform(post("/addresses/correctAddress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jSonBody)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.countryName").value("France"))
                .andExpect(jsonPath("$.stateName").value("Île-de-France"))
                .andExpect(jsonPath("$.cityName").value("Paris"));


    }
}