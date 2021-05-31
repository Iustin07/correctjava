package com.example.caa.utils.geo.info;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StateInfo {
    private int id;
    private String name;
    private int country_id;
    private String country_code;
    private String iso2;

    public StateInfo(int id, String name, int country_id, String country_code, String iso2) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
        this.country_code = country_code;
        this.iso2 = iso2;
    }

    public StateInfo(String name, int country_id, String country_code, String iso2) {
        this.name = name;
        this.country_id = country_id;
        this.country_code = country_code;
        this.iso2 = iso2;
    }
}
