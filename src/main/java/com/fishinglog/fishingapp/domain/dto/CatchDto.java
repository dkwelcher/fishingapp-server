package com.fishinglog.fishingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * Data Transfer Object for a fishing catch, encapsulating all relevant details.
 *
 * @since 2023-10-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatchDto {

    private Long catchId;

    private LocalTime time;

    private Double latitude;

    private Double longitude;

    private String species;

    private String lureOrBait;

    private String weatherCondition;

    private Integer airTemperature;

    private Integer waterTemperature;

    private Integer windSpeed;

    private TripDto trip;
}
