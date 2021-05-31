package com.example.caa.controller;

import com.example.caa.model.*;
import com.example.caa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/states")
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping(path = "getById")
    public @ResponseBody State getState(@RequestParam Integer id){
        return this.stateService.getState(id);
    }

    @GetMapping(path = "getByName")
    public @ResponseBody List<State> getStates(@RequestParam String name){
        return this.stateService.getStates(name);
    }

    @PostMapping(path = "/addAllStates")
    public ResponseEntity<String> addAllCStates(){
        return this.stateService.addAllStatesToDB();
    }
}
