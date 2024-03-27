package com.fishinglog.fishingapp.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response sent back to the user after successful authentication.
 * It contains the authentication token and user details.
 *
 * @since 2024-02-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * The JWT token generated after successful authentication.
     */
    private String token;

    /**
     * The username of the authenticated user.
     */
    private String username;

    /**
     * The ID of the authenticated user.
     */
    private Long id;
}
