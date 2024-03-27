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

/**
 * Controller for handling weather-related requests.
 *
 * @since 2024-03-19
 */
@RestController
@Log
public class WeatherController {

    private final WeatherService weatherService;

    private final OwnershipService ownershipService;

    public WeatherController(WeatherService weatherService, OwnershipService ownershipService) {
        this.weatherService = weatherService;
        this.ownershipService = ownershipService;
    }

    /**
     * Retrieves the current weather conditions based on latitude and longitude.
     *
     * @param userId The ID of the user requesting the weather information.
     * @param latitude The latitude coordinate for the weather request.
     * @param longitude The longitude coordinate for the weather request.
     * @param request The HTTP request object.
     * @return A ResponseEntity containing the WeatherDto with the current weather conditions or an error status.
     */
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
