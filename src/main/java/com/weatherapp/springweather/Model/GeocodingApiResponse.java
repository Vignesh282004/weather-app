package com.weatherapp.springweather.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeocodingApiResponse {
    private String zip;
    private String name;
    private String country;
    private double lon;
    private double lat;
    private double years;
}
