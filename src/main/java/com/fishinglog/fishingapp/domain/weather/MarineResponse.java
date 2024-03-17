package com.fishinglog.fishingapp.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarineResponse {

    private Forecast forecast;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Forecast {
        private List<ForecastDay> forecastday;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ForecastDay {
        private List<Hour> hour;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Hour {
        private double water_temp_f;
    }
}