package com.fishinglog.fishingapp.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request for authentication, containing the necessary credentials.
 *
 * @since 2024-02-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * The username provided by the user for authentication.
     */
    private String username;

    /**
     * The password provided by the user for authentication.
     */
    private String password;
}
