package com.example.caa.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertFalse;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class HttResponseTest {
    @Test
    void getElement() throws UnirestException {
        String host = "https://api.countrystatecity.in/v1/countries";
        String x_rapidApi_key = "YkpNNFppNmJlczR1ZGRYUzZDREZlUXhxSG9tQUszdzNoSVpyUm5ESw==";
        HttResponse http = new HttResponse(host, host);
        assertNotNull(http);
        HttpResponse<JsonNode> response = Unirest.get(host)
                .header("x-rapidapi-host", host)
                .header("X-CSCAPI-KEY", x_rapidApi_key)
                .asJson();
        assertNotNull(response);
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        assertNotNull(je);
    }

    @Test
    void getElemetSecond() throws UnirestException {
        String host = "https://api.countrystatecity.in/v1";
        String x_rapidApi_key = "YkpNNFppNmJlczR1ZGRYUzZDREZlUXhxSG9tQUszdzNoSVpyUm5ESw==";
        HttResponse http = new HttResponse(host, host);
        assertNotNull(host);
        assertNotNull(http);
        HttpResponse<JsonNode> response = Unirest.get(host)
                .header("x-rapidapi-host", host)
                .header("X-CSCAPI-KEY", x_rapidApi_key)
                .asJson();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        assertEquals("{\"error\":\"Not found.\"}", je.toString());
        assertNotNull(je);
    }

    @Test
    void getElemetThird() throws UnirestException {
        String host = "https://api.countrystatecity.in/v1/countries";
        String x_rapidApi_key = "";
        HttResponse http = new HttResponse(host, host);

        HttpResponse<JsonNode> response = Unirest.get(host)
                .header("x-rapidapi-host", host)
                .header("X-CSCAPI-KEY", x_rapidApi_key)
                .asJson();
        assertNotNull(response);
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.getBody().toString());
        assertEquals(je.toString(), "{\"error\":\"Unauthorized. You shouldn't be here.\"}");
    }

}