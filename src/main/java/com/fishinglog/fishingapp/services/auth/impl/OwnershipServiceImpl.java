package com.fishinglog.fishingapp.services.auth.impl;

import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.repositories.UserRepository;
import com.fishinglog.fishingapp.services.auth.JwtService;
import com.fishinglog.fishingapp.services.auth.OwnershipService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Service for handling ownership verification based on JWT tokens and user entities.
 * See {@link OwnershipService}.
 *
 * @since 2024-10-15
 */
@Service
public class OwnershipServiceImpl implements OwnershipService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Autowired
    public OwnershipServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

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
