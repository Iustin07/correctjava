package com.example.caa.controller;

import com.example.caa.service.AddressService;
import com.example.caa.utils.structures.PostalAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService addressServiceMock;

    @Test
    void getCorrectedAddress() throws Exception {
        PostalAddress data = new PostalAddress("France", "Iasi", "Paris");
        PostalAddress expectedData = new PostalAddress("France", "Île-de-France", "Paris");
        String jSonBody = "{\n \"countryName\":\"France\",\n \"stateName\":\"Iasi\",\n  \"cityName\":\"Paris\" }";
        //given(addressServiceMock.correctAddress(data)).willReturn(expectedData);
        Mockito.when(addressServiceMock.correctAddress(data)).thenReturn(expectedData);

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