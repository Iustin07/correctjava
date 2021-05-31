package com.example.caa.controller;

import com.example.caa.model.Country;
import com.example.caa.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping(path = "getByName")
    public @ResponseBody Country getCountryByName(@RequestParam String name) {
        return countryService.getCountry(name);
    }

    @GetMapping(path = "getById")
    public @ResponseBody Country getCountry(@RequestParam Short id) {
        return this.countryService.getCountry(id);
    }

    @PostMapping(path = "/addAllCountries")
    public ResponseEntity<String> addAllCountries() {
        return this.countryService.addAllCountriesToDB();
    }
}