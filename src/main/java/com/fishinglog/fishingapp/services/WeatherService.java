package com.fishinglog.fishingapp.services;

import com.fishinglog.fishingapp.domain.weather.WeatherDto;

/**
 * Service interface for accessing weather information.
 *
 * @since 2024-03-16
 */
public interface WeatherService {

    /**
     * Retrieves the current weather based on the given latitude and longitude.
     *
     * @param latitude The latitude coordinate for the weather location.
     * @param longitude The longitude coordinate for the weather location.
     * @return The current weather conditions encapsulated in a WeatherDto.
     */
    WeatherDto getCurrentWeather(double latitude, double longitude);
}
