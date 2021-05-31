package com.example.caa.utils.geo.api;

import com.example.caa.model.*;
import com.example.caa.utils.*;
import com.example.caa.utils.geo.info.*;
import com.example.caa.utils.geo.info.StateInfo;
import com.google.gson.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CityApi {
    private List<Country> countries;

    private CityInfo[] cities;
    private Gson gson;

    public CityApi(List<Country> countries) {
        this.countries = countries;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public List<CityInfo> getNewCities() {
        //1. request la tari
        //2. request la state dupa tari
        //3. request la orase dupa tara/stat

        List<CityInfo> listCities = new ArrayList<>();
        for (var country : countries) {
            ///https://api.countrystatecity.in/v1/countries/RO/states
            if (country.getCountryId() == 181)
                continue;

            //iau statele dupa tara
            String hostForStatesInCountry = "https://api.countrystatecity.in/v1/countries/" + country.getCountryCodeIso2() + "/states";
            String rapidHost = hostForStatesInCountry;

            HttResponse stateInCountry = new HttResponse(hostForStatesInCountry, rapidHost);
            JsonElement je = stateInCountry.getElement();
            StatesApi stateCountry = new StatesApi(je);

            List<StateInfo> states = stateCountry.getNewStates();
            for (var state : states) {
                //iau orasele dupa tara si stat
                String hostCitiesPerCountryState = "https://api.countrystatecity.in/v1/countries/" + country.getCountryCodeIso2() + "/states/" + state.getIso2() + "/cities";
                String rapidHostCities = hostCitiesPerCountryState;

                HttResponse citiesResponse = new HttResponse(hostCitiesPerCountryState, rapidHostCities);
                JsonElement cityJe = citiesResponse.getElement();

                this.cities = gson.fromJson(cityJe, CityInfo[].class);
                for (int k = 0; k < cities.length; ++k) {
                    cities[k].setCountry_id(country.getCountryId());
                    cities[k].setCountry_code(country.getCountryCodeIso2());
                    cities[k].setState_id(state.getId());
                    cities[k].setState_code(state.getIso2());
                    listCities.add(cities[k]);
                    //System.out.println(country.getCountryId()+" "+state.getIso2()+" "+cities[k].getName()+" "+cities[k].getId());
                }
            }

        }

        return listCities;

    }
}
