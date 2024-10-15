package com.fishinglog.fishingapp.domain.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request for user registration, containing the necessary information
 * to create a new user account.
 *
 * @since 2024-10-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]+$")
    @Size(max = 50)
    private String username;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[-!@#$%&*()_+=|<>?{}\\[\\]~]).*$")
    @Size(min = 6, max = 64)
    private String password;

    @NotNull
    @NotBlank
    @Email
    private String email;
}

