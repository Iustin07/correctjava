/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.caa.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Balint Paula
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "CITIES")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SerializedName(value = "id")
    @Column(name = "CITY_ID")
    private Integer cityId;

    @Basic(optional = false)
    @SerializedName(value = "name")
    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "STATE_CODE_ISO2")
    @SerializedName(value = "state_code")
    private String stateCodeIso2;

    @Column(name = "COUNTRY_CODE_ISO2")
    @SerializedName(value = "country_code")
    private String countryCodeIso2;

    @Column(name = "LONGITUDE")
    @SerializedName(value = "longitude")
    private Double longitude;

    @SerializedName(value = "latitude")
    @Column(name = "LATITUDE")
    private Double latitude;

    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    @JoinColumn(name = "STATE_ID", referencedColumnName = "STATE_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private State state;

    public City(Integer cityId,
                String cityName,
                Country country,
                State state) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.country = country;
        this.state = state;
    }

    public City(Integer cityId,
                String cityName,
                String stateCodeIso2,
                String countryCodeIso2,
                Double longitude,
                Double latitude,
                Country country,
                State state) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.stateCodeIso2 = stateCodeIso2;
        this.countryCodeIso2 = countryCodeIso2;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.state = state;
    }
}
