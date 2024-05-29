package com.weatherapp.springweather.Repo;

import com.weatherapp.springweather.Entity.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeatherInfoRepo extends JpaRepository<WeatherInfo,Long> {
 Optional<WeatherInfo> findByPincodeAndDate(Integer pincode, LocalDate date);
}
