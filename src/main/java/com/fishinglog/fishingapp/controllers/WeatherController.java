package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.weather.WeatherDto;
import com.fishinglog.fishingapp.services.WeatherService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // GET /weather?latitude=11.11&longitude=11.11
    @GetMapping(path = "/weather")
    public ResponseEntity<WeatherDto> getCurrentWeather(
            @RequestParam(value = "latitude") double latitude,
            @RequestParam(value = "longitude") double longitude) {

        WeatherDto weatherDto = weatherService.getCurrentWeather(latitude, longitude);
        return new ResponseEntity<>(weatherDto, HttpStatus.OK);
    }
}
