package com.example.caa.utils.geo.api;

import com.example.caa.model.Country;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CountriesApi {
    private JsonElement je;
    private Gson gson;
    private Country[] countries;

    public CountriesApi(JsonElement je, Gson gson, Country[] countries) {
        this.je = je;
        this.gson = gson;
        this.countries = countries;
    }

    public List<Country> getNewCountries(){
        this.gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        this.countries = gson.fromJson(je, Country[].class);
        return Arrays.asList(countries);
    }

}
