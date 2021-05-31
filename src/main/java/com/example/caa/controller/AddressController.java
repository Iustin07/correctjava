package com.example.caa.controller;

import com.example.caa.service.AddressService;
import com.example.caa.utils.structures.PostalAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://correctad.herokuapp.com")
@RequestMapping(path = "addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping(value="/correctAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Object> getCorrectedAddress(@RequestBody PostalAddress postalAddress) {
        PostalAddress correctedPostalAddress = this.addressService.correctAddress(postalAddress);
        if (correctedPostalAddress == null) {
            return new ResponseEntity<>("Couldn't correct address!", HttpStatus.OK);
        }
        return new ResponseEntity<>(correctedPostalAddress.toString(), HttpStatus.OK);
    }
}
