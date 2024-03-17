package com.fishinglog.fishingapp.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {

    private String weatherCondition;

    private double airTemperature;

    private double waterTemperature;

    private double windSpeed;
}
