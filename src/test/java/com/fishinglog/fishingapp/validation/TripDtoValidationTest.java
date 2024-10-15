package com.fishinglog.fishingapp.validation;

import com.fishinglog.fishingapp.domain.dto.persisted.TripDto;
import com.fishinglog.fishingapp.domain.dto.persisted.UserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripDtoValidationTest {
    private static Validator validator;
    private final LocalDate validLocalDate = LocalDate.now();
    private final String validBodyOfWater = "Lake Passes Test";
    private final UserDto validUserDto = new UserDto(1L, "TestUser", "TestPassword", "TestEmail@email.com");

    @BeforeAll()
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        TripDto validTripDto = TripDto.builder()
                .date(validLocalDate)
                .bodyOfWater(validBodyOfWater)
                .user(validUserDto)
                .build();

        Set<ConstraintViolation<TripDto>> violations = validator.validate(validTripDto);
        assertTrue(violations.isEmpty(), "There should be no violations.");
    }

    @Test
    void whenDateIsNull_thenViolation() {
        TripDto invalidTripDto = TripDto.builder()
                .date(null)
                .bodyOfWater(validBodyOfWater)
                .user(validUserDto)
                .build();

        Set<ConstraintViolation<TripDto>> violations = validator.validate(invalidTripDto);
        assertFalse(violations.isEmpty(), "There should be violations when date is null");
    }

    @Test
    void whenBodyOfWaterIsNull_thenViolations() {
        TripDto invalidTripDto = TripDto.builder()
                .date(validLocalDate)
                .bodyOfWater(null)
                .user(validUserDto)
                .build();

        Set<ConstraintViolation<TripDto>> violations = validator.validate(invalidTripDto);
        assertFalse(violations.isEmpty(), "There should be violations when bodyOfWater is null");
    }

    @Test
    void whenBodyOfWaterIsBlank_thenViolations() {
        TripDto invalidTripDto = TripDto.builder()
                .date(validLocalDate)
                .bodyOfWater("")
                .user(validUserDto)
                .build();

        Set<ConstraintViolation<TripDto>> violations = validator.validate(invalidTripDto);
        assertFalse(violations.isEmpty(), "There should be violations when bodyOfWater is blank");
    }

    @Test
    void whenBodyOfWaterDoesntMatchRegex_thenViolations() {
        TripDto invalidTripDto = TripDto.builder()
                .date(validLocalDate)
                .bodyOfWater("Lake Murr@y")
                .user(validUserDto)
                .build();

        Set<ConstraintViolation<TripDto>> violations = validator.validate(invalidTripDto);
        assertFalse(violations.isEmpty(), "There should be violations when bodyOfWater doesn't match regex");
    }

    @Test
    void whenBodyOfWaterExceedMaxSize_thenViolations() {
        TripDto invalidTripDto = TripDto.builder()
                .date(validLocalDate)
                .bodyOfWater("Lake Thurmond".repeat(10))
                .user(validUserDto)
                .build();

        Set<ConstraintViolation<TripDto>> violations = validator.validate(invalidTripDto);
        assertFalse(violations.isEmpty(), "There should be violations when bodyOfWater exceeds max size");
    }

    @Test
    void whenUserDtoisNull_thenViolation() {
        TripDto invalidTripDto = TripDto.builder()
                .date(validLocalDate)
                .bodyOfWater(validBodyOfWater)
                .user(null)
                .build();

        Set<ConstraintViolation<TripDto>> violations = validator.validate(invalidTripDto);
        assertFalse(violations.isEmpty(), "There should be violations when user is null");
    }
}
