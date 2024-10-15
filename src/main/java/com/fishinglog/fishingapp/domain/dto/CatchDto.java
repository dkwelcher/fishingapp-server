package com.fishinglog.fishingapp.domain.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * Data Transfer Object for a fishing catch, encapsulating all relevant details.
 *
 * @since 2024-10-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatchDto {

    private Long catchId;

    @NotNull
    private LocalTime time;

    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private Double latitude;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private Double longitude;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    @Size(max = 50)
    private String species;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    @Size(max = 50)
    private String lureOrBait;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$")
    @Size(max = 25)
    private String weatherCondition;

    @NotNull
    @Min(value = -50)
    @Max(value = 150)
    private Integer airTemperature;

    @NotNull
    @Min(value = -50)
    @Max(value = 150)
    private Integer waterTemperature;

    @NotNull
    @Min(value = 0)
    @Max(value = 150)
    private Integer windSpeed;

    @NotNull
    private TripDto trip;
}
