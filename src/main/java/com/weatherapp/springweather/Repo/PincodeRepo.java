package com.weatherapp.springweather.Repo;

import com.weatherapp.springweather.Entity.Pincode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeRepo extends JpaRepository<Pincode,Integer> {
}
