package com.fishinglog.fishingapp.services.impl;

import com.fishinglog.fishingapp.domain.weather.MarineResponseDto;
import com.fishinglog.fishingapp.domain.weather.WeatherDto;
import com.fishinglog.fishingapp.domain.weather.WeatherResponseDto;
import com.fishinglog.fishingapp.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service implementation to fetch weather data from an external API.
 *
 * @since 2024-03-16
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    /**
     * Retrieves the current weather conditions for a specific latitude and longitude.
     *
     * @param latitude  The latitude of the location.
     * @param longitude The longitude of the location.
     * @return A WeatherDto object containing the current weather conditions.
     */
    @Override
    public WeatherDto getCurrentWeather(double latitude, double longitude) {

        final String weatherUrl = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + latitude + "," + longitude;

        WeatherResponseDto weatherResponse = restTemplate.getForObject(weatherUrl, WeatherResponseDto.class);
        WeatherResponseDto.Current currentWeather = weatherResponse.getCurrent();

        String weatherCondition = getWeatherCondition(currentWeather);

        double airTemperature = currentWeather.getTemp_f();
        double windSpeed = currentWeather.getWind_mph();

        final String marineUrl = "http://api.weatherapi.com/v1/marine.json?key=" + apiKey + "&q=" + latitude + "," + longitude;

        MarineResponseDto marineResponse = restTemplate.getForObject(marineUrl, MarineResponseDto.class);
        List<MarineResponseDto.ForecastDay> forecastDays = marineResponse.getForecast().getForecastday();

        double waterTemperature = -900;
        if(!forecastDays.isEmpty()) {
            List<MarineResponseDto.Hour> hours = forecastDays.get(0).getHour();
            if(!hours.isEmpty()) {
                waterTemperature = hours.get(0).getWater_temp_f();
            }
        }

        return new WeatherDto(weatherCondition, airTemperature, waterTemperature, windSpeed);
    }

    /**
     * Determines the weather condition based on the weather code.
     *
     * @param currentWeather The current weather data.
     * @return A string describing the weather condition.
     */
    private static String getWeatherCondition(WeatherResponseDto.Current currentWeather) {
        int code = currentWeather.getCondition().getCode();
        String weatherCondition;
        if(code == 1000) {
            weatherCondition = "clear";
        } else if(code == 1003) {
            weatherCondition = "partly cloudy";
        } else if(code == 1006) {
            weatherCondition = "cloudy";
        } else if(code == 1009 || code == 1030 || code == 1063 || code == 1066 || code == 1069 || code == 1072 || code == 1087 || code == 1135 || code == 1147) {
            weatherCondition = "overcast";
        } else if(code == 1150 || code == 1153 || code == 1168 || code == 1180 || code == 1183 || code == 1198 || code == 1204 || code == 1210 || code == 1213 || code == 1240 || code == 1249 || code == 1255 || code == 1261 || code == 1273 || code == 1279) {
            weatherCondition = "light precipitation";
        } else if(code == 1186 || code == 1189 || code == 1201 || code == 1207 || code == 1216 || code == 1219 || code == 1243 || code == 1252 || code == 1258 || code == 1264 || code == 1276 || code == 1282) {
            weatherCondition = "moderate precipitation";
        } else if(code == 1114 || code == 1117 || code == 1171 || code == 1192 || code == 1195 || code == 1222 || code == 1225 || code == 1237 || code == 1246) {
            weatherCondition = "heavy precipitation";
        } else {
            weatherCondition = "unknown";
        }
        return weatherCondition;
    }
}
