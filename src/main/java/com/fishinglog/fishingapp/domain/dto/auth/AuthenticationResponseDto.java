package com.fishinglog.fishingapp.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response sent back to the user after successful authentication.
 * It contains the authentication token and user details.
 *
 * @since 2024-10-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {

    /**
     * The JWT token generated after successful authentication.
     */
    private String token;
    private String username;
    private Long id;
}
