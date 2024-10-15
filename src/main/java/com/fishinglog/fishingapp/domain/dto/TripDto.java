package com.fishinglog.fishingapp.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Data Transfer Object for a fishing trip, encapsulating all relevant details.
 *
 * @since 2024-10-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripDto {

    private Long tripId;

    @NotNull
    private LocalDate date;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9 ]*$")
    @Size(max = 100)
    private String bodyOfWater;

    @NotNull
    private UserDto user;
}
