package com.fishinglog.fishingapp.services.auth;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Interface for defining services that handle ownership verification based on JWT tokens and user entities.
 *
 * @since 2024-10-15
 */
public interface OwnershipService {

    /**
     * Checks if the username in the JWT token matches the username of the user identified by the provided user ID.
     *
     * @param userId The ID of the user to verify ownership against.
     * @param request The HTTP servlet request containing the JWT token.
     * @return true if the username in the token matches the user's username, false otherwise.
     */
    boolean doesRequestUsernameMatchTokenUsername(Long userId, HttpServletRequest request);
}
