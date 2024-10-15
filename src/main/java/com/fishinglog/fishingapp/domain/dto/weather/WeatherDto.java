package com.fishinglog.fishingapp.domain.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for a weather conditions, encapsulating all relevant details.
 *
 * @since 2024-03-16
 */
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
