package com.example.caa.utils.geo.info;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CityInfo {
    private int id;
    private String name;
    private int state_id;
    private String state_code;
    private int country_id;
    private String country_code;
    private String latitude;
    private String longitude;


    public CityInfo(int id,
                    String name,
                    int state_id,
                    String state_code,
                    int country_id,
                    String country_code,
                    String latitude,
                    String longitude) {
        this.id = id;
        this.name = name;
        this.state_id = state_id;
        this.state_code = state_code;
        this.country_id = country_id;
        this.country_code = country_code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CityInfo(int id, String name,
                    int state_id, String state_code,
                    int country_id, String country_code) {
        this.id = id;
        this.name = name;
        this.state_id = state_id;
        this.state_code = state_code;
        this.country_id = country_id;
        this.country_code = country_code;
    }
}
