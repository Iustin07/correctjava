/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.caa.utils.structures;

import com.example.caa.model.City;
import com.example.caa.model.Country;
import com.example.caa.model.State;
import lombok.*;

/**
 *
 * @author Balint Paula
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AddressData {
    private Country country;
    private State state;
    private City city;

    public AddressData(Country country, State state, City city) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

}
