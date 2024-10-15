package com.fishinglog.fishingapp.services.auth;

import com.fishinglog.fishingapp.domain.dto.auth.AuthenticationRequestDto;
import com.fishinglog.fishingapp.domain.dto.auth.AuthenticationResponseDto;
import com.fishinglog.fishingapp.domain.dto.auth.RegisterRequestDto;

/**
 * Interface defining service operations for handling authentication and registration logic.
 *
 * @since 2024-10-15
 */
public interface AuthenticationService {
    /**
     * Registers a new user with the provided credentials.
     *
     * @param registerRequestDto The registration request containing the user's credentials.
     * @return An {@link AuthenticationResponseDto} containing the new user's JWT token.
     */
    AuthenticationResponseDto register(RegisterRequestDto registerRequestDto);

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param authenticationRequestDto The authentication request containing the user's credentials.
     * @return An {@link AuthenticationResponseDto} containing the user's JWT token along with user details.
     */
    AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
}
