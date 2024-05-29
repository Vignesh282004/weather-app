package com.weatherapp.springweather.Services;

import com.weatherapp.springweather.Entity.Pincode;
import com.weatherapp.springweather.Entity.WeatherInfo;
import com.weatherapp.springweather.Model.GeocodingApiResponse;
import com.weatherapp.springweather.Model.WeatherApiResponse;
import com.weatherapp.springweather.Repo.PincodeRepo;
import com.weatherapp.springweather.Repo.WeatherInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class WeatherService {
    @Autowired
    private WeatherInfoRepo weatherInfoRepo;
    @Autowired
    private PincodeRepo pincodeRepo;

    private String OPEN_WEATHER_API_KEY = "9827859fccb26915aec4bbea453b06b9";

    public Pincode getpinCode(Integer pincode) throws  Exception
    {
        String url = "https://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",in&appid=" + OPEN_WEATHER_API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GeocodingApiResponse> geocodingApiResponseResponseEntity = restTemplate.getForEntity(url, GeocodingApiResponse.class);
        double longitude=0;
        double latitide=0;
        if(geocodingApiResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
            latitide = geocodingApiResponseResponseEntity.getBody().getLat();
            longitude = geocodingApiResponseResponseEntity.getBody().getLon();
        }
        else
        {
            throw new Exception(geocodingApiResponseResponseEntity.toString());
        }
        return new Pincode(pincode,latitide,longitude);
     }

     public WeatherApiResponse getrep(double latitude, double longitude, LocalDate date) throws  Exception
     {
         long unixTimes = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
         String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid="
             + OPEN_WEATHER_API_KEY + "&dt=" + unixTimes;

         RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<WeatherApiResponse> respnse = restTemplate.getForEntity(url,WeatherApiResponse.class);
        if(respnse.getStatusCode().isError()) {
            throw new Exception(respnse.getStatusCode().toString());
        }
        return respnse.getBody();
     }

     public WeatherInfo getWeatherInfo(Integer pincode,LocalDate date) throws  Exception
     {
         Optional<WeatherInfo> weatherInfo = this.weatherInfoRepo.findByPincodeAndDate(pincode,date);
         if(weatherInfo.isPresent())
             return weatherInfo.get();
         double latitude,longitude;
         Optional<Pincode> pincodeOptional=null;
         try {
             pincodeOptional = this.pincodeRepo.findById(pincode);
         }catch (DataIntegrityViolationException e) {
             e.printStackTrace();
         }
         if(pincodeOptional.isPresent()) {
             latitude = pincodeOptional.get().getLatitude();
             longitude = pincodeOptional.get().getLongitude();
         }
         else
         {
             Pincode pincode1 = getpinCode(pincode);
             latitude = pincode1.getLatitude();
             longitude = pincode1.getLongitude();
             this.pincodeRepo.save(pincode1);
         }

         WeatherApiResponse weatherApiResponse = getrep(latitude,longitude,date);

         WeatherInfo weatherInfo1 = new WeatherInfo();
         if(weatherApiResponse != null)
         {
             weatherInfo1.setDate(date);
             weatherInfo1.setPincode(pincode);
             weatherInfo1.setTemperature(weatherApiResponse.getMain().getTemp());
             weatherInfo1.setPressure(weatherApiResponse.getMain().getPressure());
             weatherInfo1.setHumidity(weatherApiResponse.getMain().getHumidity());
             weatherInfo1.setDescription(weatherApiResponse.getWeather().get(0).getDescription());
             weatherInfo1.setWindSpeed(weatherApiResponse.getWind().getSpeed());
            weatherInfo1.setPlace(weatherApiResponse.getName());
            this.weatherInfoRepo.save(weatherInfo1);
         }
         System.out.println(weatherInfo1.toString());
        return weatherInfo1;
     }
}
