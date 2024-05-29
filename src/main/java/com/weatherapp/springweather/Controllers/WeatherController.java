package com.weatherapp.springweather.Controllers;

import com.weatherapp.springweather.Entity.WeatherInfo;
import com.weatherapp.springweather.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    @GetMapping("/weather")
    public ResponseEntity<WeatherInfo> getme(@RequestParam Integer pincode, @RequestParam  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date)
    {
        WeatherInfo weatherInfo = null;

        try {
            weatherInfo = this.weatherService.getWeatherInfo(pincode, date);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(weatherInfo);
    }
}
