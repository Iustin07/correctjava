package com.example.caa.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class HttResponse {
    private String host;
    private String x_rapidApi_host;
    private String x_rapidApi_key = "YkpNNFppNmJlczR1ZGRYUzZDREZlUXhxSG9tQUszdzNoSVpyUm5ESw==";//Type here your key

    public HttResponse(String host, String x_rapidApi_host) {
        this.host = host;
        this.x_rapidApi_host = x_rapidApi_host;
    }

    public JsonElement getElement() {
        try {
            HttpResponse<JsonNode> response = Unirest.get(host)
                    .header("x-rapidapi-host", x_rapidApi_host)
                    .header("X-CSCAPI-KEY", x_rapidApi_key)
                    .asJson();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(response.getBody().toString());
            return je;
        } catch (UnirestException ex) {
            System.out.println("Eroare HttpResponse");
            Logger.getLogger(HttResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
