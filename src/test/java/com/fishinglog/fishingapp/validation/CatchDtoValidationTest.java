package com.fishinglog.fishingapp.validation;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.dto.TripDto;
import com.fishinglog.fishingapp.domain.dto.UserDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CatchDtoValidationTest {
    private static Validator validator;
    private final LocalTime validTime = LocalTime.now();
    private final Double validLatitude = 50.0;
    private final Double validLongitude = 50.0;
    private final String validSpecies = "Striped Bass";
    private final String validLureOrBait = "Blueback Herring";
    private final String validWeatherCondition = "clear";
    private final Integer validAirTemperature = 80;
    private final Integer validWaterTemperature = 78;
    private final Integer validWindSpeed = 8;
    private final TripDto validTripDto = new TripDto(1L, LocalDate.now(), "Lake Murray", new UserDto());

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        CatchDto validCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(validCatchDto);
        assertTrue(violations.isEmpty(), "There should be no violations");
    }

    @Test
    void whenTimeIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(null)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when time is null");
    }

    @Test
    void whenLatitudeIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(null)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when latitude is null");
    }

    @Test
    void whenLatitudeExceedsMaxValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(90.00001)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when latitude exceeds max value");
    }

    @Test
    void whenLatitudeBelowMinValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(-90.00001)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when latitude is below min value");
    }

    @Test
    void whenLongitudeIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(null)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when longitude is null");
    }

    @Test
    void whenLongitudeExceedsMaxValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(180.0001)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when longitude exceeds max value");
    }

    @Test
    void whenLongitudeBelowMinValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(-181.0)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when longitude is below min value");
    }

    @Test
    void whenSpeciesIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(null)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when species is null");
    }

    @Test
    void whenSpeciesIsBlank_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species("")
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when species is blank");
    }

    @Test
    void whenSpeciesDoesntMatchRegex_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species("Str1ped Bass")
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when species doesn't match regex");
    }

    @Test
    void whenSpeciesExceedsMaxSize_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species("Striped Bass".repeat(10))
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when species exceeds max size");
    }

    @Test
    void whenLureOrBaitNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(null)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when lureOrBait is null");
    }

    @Test
    void whenLureOrBaitIsBlank_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait("")
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when lureOrBait is blank");
    }

    @Test
    void whenLureOrBaitDoesntMatchRegex_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait("Blueback Herr1ng")
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when lureOrBait doesn't match regex");
    }

    @Test
    void whenLureOrBaitExceedsMaxSize_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait("Blueback Herring".repeat(10))
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when lureOrBait exceeds max size");
    }

    @Test
    void whenWeatherConditionIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(null)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when weatherCondition is null");
    }

    @Test
    void whenWeatherConditionIsBlank_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition("")
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when weatherCondition is blank");
    }

    @Test
    void whenWeatherConditionDoesntMatchRegex_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition("cle@r")
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when weatherCondition doesn't match regex");
    }

    @Test
    void whenWeatherConditionExceedsMaxSize_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition("clear".repeat(15))
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when weatherCondition exceeds max size");
    }

    @Test
    void whenAirTemperatureIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(null)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when airTemperature is null");
    }

    @Test
    void whenAirTemperatureExceedsMaxValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(170)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when airTemperature exceeds max value");
    }

    @Test
    void whenAirTemperatureBelowMinValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(-52)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when airTemperature is below min value");
    }

    @Test
    void whenWaterTemperatureIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(null)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when waterTemperature is null");
    }

    @Test
    void whenWaterTemperatureExceedsMaxValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(151)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when waterTemperature exceeds max value");
    }

    @Test
    void whenWaterTemperatureBelowMinValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(-51)
                .windSpeed(validWindSpeed)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when waterTemperature is below min value");
    }

    @Test
    void whenWindSpeedIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(null)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when windSpeed is null");
    }

    @Test
    void whenWindSpeedExceedsMaxValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(151)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when windSpeed exceeds max value");
    }

    @Test
    void whenWindSpeedBelowMinValue_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(-1)
                .trip(validTripDto)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when windSpeed is below min value");
    }

    @Test
    void whenTripDtoIsNull_thenViolation() {
        CatchDto invalidCatchDto = CatchDto.builder()
                .time(validTime)
                .latitude(validLatitude)
                .longitude(validLongitude)
                .species(validSpecies)
                .lureOrBait(validLureOrBait)
                .weatherCondition(validWeatherCondition)
                .airTemperature(validAirTemperature)
                .waterTemperature(validWaterTemperature)
                .windSpeed(validWindSpeed)
                .trip(null)
                .build();

        Set<ConstraintViolation<CatchDto>> violations = validator.validate(invalidCatchDto);
        assertFalse(violations.isEmpty(), "There should be violations when TripDto is null");
    }
}
