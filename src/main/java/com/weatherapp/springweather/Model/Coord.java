package com.weatherapp.springweather.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coord {
    private double lon;
    private  double lat;
    private double years;

    public Coord(double lon,double years) {
        this.lon = lon;
        this.years = years;
    }
}
