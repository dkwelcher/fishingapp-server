package com.fishinglog.fishingapp.services.auth;

import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Service for handling ownership verification based on JWT tokens and user entities.
 *
 * @since 2024-02-29
 */
@Service
@RequiredArgsConstructor
public class OwnershipService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    /**
     * Checks if the username in the JWT token matches the username of the user identified by the provided user ID.
     *
     * @param userId The ID of the user to verify ownership against.
     * @param request The HTTP servlet request containing the JWT token.
     * @return true if the username in the token matches the user's username, false otherwise.
     */
    public boolean doesRequestUsernameMatchTokenUsername(Long userId, HttpServletRequest request) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if(userEntity.isEmpty()) {
            return false;
        }

        final String token = extractToken(request);
        final String tokenUsername = jwtService.extractUsername(token);

        return (userEntity.get().getUsername().equals(tokenUsername));
    }

    /**
     * Extracts the JWT token from the HTTP request.
     *
     * @param request The HTTP servlet request containing the JWT token in the Authorization header.
     * @return The extracted JWT token or null if no token is found or the token is improperly formatted.
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
