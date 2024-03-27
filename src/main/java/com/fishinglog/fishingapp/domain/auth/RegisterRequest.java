package com.fishinglog.fishingapp.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request for user registration, containing the necessary information
 * to create a new user account.
 *
 * @since 2024-02-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    /**
     * The username chosen by the user for the new account.
     */
    private String username;

    /**
     * The password chosen by the user for the new account.
     */
    private String password;

    /**
     * The email address provided by the user for account registration.
     */
    private String email;
}

