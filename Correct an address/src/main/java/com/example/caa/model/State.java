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
 *
 * @author Balint Paula
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "STATES")
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SerializedName(value = "id", alternate = "stateid")
    @Column(name = "STATE_ID")
    private Integer stateId;

    @Basic(optional = false)
    @SerializedName(value = "name", alternate = "statename")
    @Column(name = "STATE_NAME")
    private String stateName;

    @SerializedName(value = "country_code", alternate = "country_codeiso2")
    @Column(name = "COUNTRY_CODE_ISO2")
    private String countryCodeIso2;

    @SerializedName(value = "iso2", alternate = "stateiso2")
    @Column(name = "STATE_CODE_ISO2")
    private String stateCodeIso2;

    @SerializedName(value = "country_id", alternate = "countryid")
    @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "COUNTRY_ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Country country;

    public State(Integer stateId, String stateName, String countryCodeIso2, String stateCodeIso2, Country country) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.countryCodeIso2 = countryCodeIso2;
        this.stateCodeIso2 = stateCodeIso2;
        this.country = country;
    }
}
