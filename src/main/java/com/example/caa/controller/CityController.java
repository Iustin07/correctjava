package com.example.caa.controller;

import com.example.caa.model.City;
import com.example.caa.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping(path = "/addAllCities")
    public ResponseEntity<Object> addAllCStates(){
        return this.cityService.addAllCitiesToDB();
    }

    @GetMapping(path = "/getById")
    public @ResponseBody City getCity(@RequestParam Integer id){
        return this.cityService.getCity(id);
    }

    @GetMapping(path = "/getByName")
    public @ResponseBody List<City> getCities(@RequestParam String name){
        return this.cityService.getCities(name);
    }
}
