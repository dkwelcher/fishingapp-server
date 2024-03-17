package com.fishinglog.fishingapp.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherResponse {

    private Current current;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Current {
        private double temp_f;
        private double wind_mph;
        private double precip_in;
        private int cloud;
        private Condition condition;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Condition {
        private int code;
    }


}
