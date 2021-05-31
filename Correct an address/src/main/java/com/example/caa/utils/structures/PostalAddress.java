package com.example.caa.utils.structures;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PostalAddress {

    private String countryName;
    private String stateName;
    private String cityName;

    public PostalAddress(String countryName, String stateName, String cityName) {
        this.countryName = countryName;
        this.stateName = stateName;
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"countryName\" : \"" + countryName + "\",\n" +
                "  \"stateName\" : \"" + stateName + "\",\n" +
                "  \"cityName\" : \"" + cityName + "\" \n" +
                '}';
    }
}
