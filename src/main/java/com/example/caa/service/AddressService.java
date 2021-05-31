package com.example.caa.service;

import com.example.caa.model.City;
import com.example.caa.model.Country;
import com.example.caa.model.State;
import com.example.caa.utils.structures.AddressData;
import com.example.caa.utils.structures.PostalAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    private final CountryService countryService;
    private final StateService stateService;
    private final CityService cityService;

    @Autowired
    public AddressService(CountryService countryService, StateService stateService, CityService cityService) {
        this.countryService = countryService;
        this.stateService = stateService;
        this.cityService = cityService;
    }

    public @ResponseBody PostalAddress correctAddress(PostalAddress postalAddress) {
        PostalAddress correctedPostalAddress = new PostalAddress();

        System.out.println(postalAddress.getCountryName() + " --- " +
                postalAddress.getStateName() + " --- " + postalAddress.getCityName());

        Country country;
        if(postalAddress.getCountryName().length() > 2){
            String name = postalAddress.getCountryName().substring(0, 1).toUpperCase() +
                    postalAddress.getCountryName().substring(1).toLowerCase();
            country = countryService.getCountry(name);
        }
        else{
            String code = postalAddress.getCountryName().toUpperCase();
            country = countryService.getCountryByCode(code);
        }

        List<State> states;
        if(postalAddress.getStateName().length() >= 1) {
            String stateName = postalAddress.getStateName().substring(0, 1).toUpperCase() +
                    postalAddress.getStateName().substring(1).toLowerCase();
            states = stateService.getStates(stateName);
        }
        else{
            states = stateService.getStates(postalAddress.getStateName());
        }

        List<City> cities;
        if(postalAddress.getCityName().length() >= 1) {
            String cityName = postalAddress.getCityName().substring(0, 1).toUpperCase() +
                    postalAddress.getCityName().substring(1).toLowerCase();
            cities = cityService.getCities(cityName);
        }
        else{
            cities = cityService.getCities(postalAddress.getCityName());
        }

        if (country == null && states.size() == 0
                || country == null && cities.size() == 0
                || states.size() == 0 && cities.size() == 0) {
            System.out.println("Two are empty -> couldn't find results");
            return null;
        }

        //checking if city is in state
        AddressData addressData = new AddressData();
        for (int index = 0; index < cities.size(); index++) {
            for (int indexState = 0; indexState < states.size(); indexState++) {
                if (states.get(indexState).getStateId().compareTo(cities.get(index).getState().getStateId()) == 0) {
                    addressData.setCity(cities.get(index));
                    addressData.setState(states.get(indexState));
                    break;
                }
            }
        }

        //city is not in state
        if (addressData.getState() == null) {
            //city may be in country
            System.out.println("city may be in country");
            for (int index = 0; index < cities.size(); index++) {
                if (country.getCountryId().compareTo(cities.get(index).getCountry().getCountryId()) == 0) {
                    addressData.setCity(cities.get(index));
                    addressData.setCountry(country);
                    break;
                }
            }

            if (addressData.getCity() == null) {
                System.out.println("city is null");
                //city not in state or country -> address can't be corrected or city is wrong
                //checking if state is in country

                for (int indexState = 0; indexState < states.size(); indexState++) {
                    if (states.get(indexState).getCountry().getCountryId().compareTo(country.getCountryId()) == 0) {
                        addressData.setCountry(country);
                        addressData.setState(states.get(indexState));
                        break;
                    }
                }

                if (addressData.getState() == null) {
                    //address can't be corrected
                    return null;
                }
                //address can be corrected
                //trying a match for letters
                List<City> cityCandidates = new ArrayList<>();

                //we already know state is in country
                //finding a city that is in state
                if (postalAddress.getCityName() != null) {
                    for (int letters = 0; letters < postalAddress.getCityName().length()
                            && addressData.getCity() == null; letters++) {
                        cityCandidates = this.cityService.getAllByStateAndNameStartingWith(addressData.getState(),
                                postalAddress.getCityName().substring(0, postalAddress.getCityName().length() - letters));
                        if (cityCandidates.size() != 0) {
                            for (int indexCandidates = 0; indexCandidates < cityCandidates.size()
                                    && addressData.getCity() == null; indexCandidates++) {
                                if (addressData.getState().getStateId()
                                        .compareTo(cityCandidates.get(indexCandidates).getState().getStateId()) == 0) {
                                    addressData.setCity(cityCandidates.get(indexCandidates));
                                }
                            }
                        }
                    }
                }
                if (addressData.getCity() == null) {
                    //couldn't find a partial match for city name, so returning a random one
                    City city = cityService.getRandomCityInState(addressData.getState());
                    addressData.setCity(city);
                }
            } else {
                //city in country, state wrong
                State state = stateService.getState(addressData.getCity().getState().getStateId());
                if (state != null) {
                    addressData.setState(state);
                }
            }

        } else {
            //city in state, correcting country
            Country country1 = countryService.getCountry(addressData.getState().getCountry().getCountryId());
            addressData.setCountry(country1);
        }
        if (addressData.getCountry() != null) {
            if(postalAddress.getCountryName().length() > 2) {
                correctedPostalAddress.setCountryName(addressData.getCountry().getCountryName());
            }
            else{
                correctedPostalAddress.setCountryName(addressData.getCountry().getCountryCodeIso2());
            }
        }
        if (addressData.getState() != null) {
            correctedPostalAddress.setStateName(addressData.getState().getStateName());
        }
        if (addressData.getCity() != null) {
            correctedPostalAddress.setCityName(addressData.getCity().getCityName());
        }

        System.out.println(correctedPostalAddress.getCountryName() + " --- " +
                correctedPostalAddress.getStateName() + " --- " +
                correctedPostalAddress.getCityName());
        return correctedPostalAddress;
    }
}
