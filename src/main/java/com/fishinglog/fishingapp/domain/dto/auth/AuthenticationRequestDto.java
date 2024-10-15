package com.fishinglog.fishingapp.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request for authentication, containing the necessary credentials.
 *
 * @since 2024-10-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {

    private String username;
    private String password;
}
