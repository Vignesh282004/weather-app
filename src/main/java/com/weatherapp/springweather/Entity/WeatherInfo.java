package com.weatherapp.springweather.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather_info")
@Entity
public class WeatherInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer pincode;

    private String  place;

    private LocalDate date;

    private double temperature;

    private int humidity;

    private int pressure;

    private double windSpeed;

    private String  description;
}
