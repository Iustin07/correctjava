package com.example.caa.service;

import com.example.caa.utils.geo.api.CountriesApi;
import com.example.caa.utils.*;
import com.example.caa.model.Country;
import com.example.caa.repository.CountryRepository;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country getCountry(Short id) {
        Country country = this.countryRepository.findById(id).get();
        return country;
    }

    public Country getCountry(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        return this.countryRepository.findFirstByCountryName(name);
    }

    public Country getCountryByCode(String codeIso2){
        if(codeIso2 == null){
            throw new IllegalArgumentException("Code cannot be null");
        }
        return this.countryRepository.findByCountryCodeIso2(codeIso2);
    }

    public ResponseEntity<String> addCountry(Country country) {
        if (country == null) {
            return new ResponseEntity<>("Country cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        this.countryRepository.save(country);
        return new ResponseEntity<>("Country added successfully!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> addAllCountriesToDB() {
        CountriesApi countriesApi = new CountriesApi();
        HttResponse ht = new HttResponse(GeoDbRequest.host1, GeoDbRequest.host1);
        JsonElement je = ht.getElement();
        countriesApi.setJe(je);
        List<Country> newCountries = countriesApi.getNewCountries();
        for (Country country : newCountries) {
            if(country.getCountryId() != 181) {
                addCountry(country);
                System.out.println(country);
            }
        }
        return new ResponseEntity<>("Countries added successfully!", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteCountry(Country country) {
        if (country == null) {
            return new ResponseEntity<>("Country cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        this.countryRepository.delete(country);
        return new ResponseEntity<>("Country deleted successfully!", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCountryById(Short id) {
        if (id == null) {
            return new ResponseEntity<>("Cannot delete an empty country!", HttpStatus.BAD_REQUEST);
        }
        this.countryRepository.delete(getCountry(id));
        return new ResponseEntity<>("Country deleted successfully!", HttpStatus.OK);
    }
}
