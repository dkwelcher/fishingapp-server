package com.fishinglog.fishingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDto {

    private Long tripId;

    private LocalDate date;

    private String bodyOfWater;

    private UserDto user;
}
