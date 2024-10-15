package com.fishinglog.fishingapp.validation;

import com.fishinglog.fishingapp.domain.auth.RegisterRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterRequestDtoValidationTest {
    private static Validator validator;
    private final String validUsername = "testUser123";
    private final String validPassword = "testPassword@123";
    private final String validEmail = "testEmail123@gmail.com";

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void whenAllFieldsValid_thenNoViolations() {
        RegisterRequestDto validRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password(validPassword)
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(validRegisterRequestDto);
        assertTrue(violations.isEmpty(), "There should be no violations");
    }

    @Test
    void whenUsernameIsNull_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(null)
                .password(validPassword)
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when username is null");
    }

    @Test
    void whenUsernameIsEmpty_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username("")
                .password(validPassword)
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when username is empty");
    }

    @Test
    void whenUsernameDoesntMatchRegex_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username("test spaces")
                .password(validPassword)
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when username doesn't match regex");
    }

    @Test
    void whenUsernameExceedMaxSize_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username("testuser".repeat(10))
                .password(validPassword)
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when username exceeds max size");
    }

    @Test
    void whenPasswordIsNull_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password(null)
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when password is null");
    }

    @Test
    void whenPasswordIsEmpty_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password("")
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when password is empty");
    }

    @Test
    void whenPasswordDoesntMatchRegex_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password("password")
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when password doesn't match regex");
    }

    @Test
    void whenPasswordBelowMinSize_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password("pa55*")
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when password is below min size");
    }

    @Test
    void whenPasswordExceedsMaxSize_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password("pa55*word".repeat(10))
                .email(validEmail)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when password exceeds max size");
    }

    @Test
    void whenEmailIsNull_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password(validPassword)
                .email(null)
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when email is null");
    }

    @Test
    void whenEmailIsBlank_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password(validPassword)
                .email("")
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when email is blank");
    }

    @Test
    void whenEmailIsIncorrectFormat_thenViolations() {
        RegisterRequestDto invalidRegisterRequestDto = RegisterRequestDto.builder()
                .username(validUsername)
                .password(validPassword)
                .email("testemail.com")
                .build();

        Set<ConstraintViolation<RegisterRequestDto>> violations = validator.validate(invalidRegisterRequestDto);
        assertFalse(violations.isEmpty(), "There should be violations when email is incorrectly formatted");
    }
}
