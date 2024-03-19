package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.weather.WeatherDto;
import com.fishinglog.fishingapp.services.WeatherService;
import com.fishinglog.fishingapp.services.auth.OwnershipService;
import jakarta.servlet.http.HttpServletRequest;
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

    private final OwnershipService ownershipService;

    public WeatherController(WeatherService weatherService, OwnershipService ownershipService) {
        this.weatherService = weatherService;
        this.ownershipService = ownershipService;
    }

    // GET /weather?userId=123&latitude=11.11&longitude=11.11
    @GetMapping(path = "/weather")
    public ResponseEntity<WeatherDto> getCurrentWeather(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "latitude") double latitude,
            @RequestParam(value = "longitude") double longitude,
            HttpServletRequest request) {

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        WeatherDto weatherDto = weatherService.getCurrentWeather(latitude, longitude);
        return new ResponseEntity<>(weatherDto, HttpStatus.OK);
    }
}
