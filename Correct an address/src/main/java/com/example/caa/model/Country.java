/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.caa.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Balint Paula
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "COUNTRIES")
public class Country implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COUNTRY_ID")
    @SerializedName(value = "id", alternate = "countryid")
    private Short countryId;

    @Basic(optional = false)
    @SerializedName(value = "name", alternate = "countryName")
    @Column(name = "COUNTRY_NAME")
    private String countryName;

    @SerializedName(value = "iso2", alternate = "countryiso2")
    @Column(name = "COUNTRY_CODE_ISO2")
    private String countryCodeIso2;

    public Country(Short countryId, String countryName, String countryCodeIso2) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryCodeIso2 = countryCodeIso2;
    }
}
