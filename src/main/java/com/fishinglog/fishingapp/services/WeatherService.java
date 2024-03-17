package com.fishinglog.fishingapp.services;

import com.fishinglog.fishingapp.domain.weather.WeatherDto;

public interface WeatherService {

    WeatherDto getCurrentWeather(double latitude, double longitude);
}
