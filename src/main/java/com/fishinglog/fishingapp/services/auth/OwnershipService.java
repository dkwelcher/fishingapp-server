package com.fishinglog.fishingapp.services.auth;

import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnershipService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    public boolean doesRequestUsernameMatchTokenUsername(Long userId, HttpServletRequest request) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if(userEntity.isEmpty()) {
            return false;
        }

        final String token = extractToken(request);
        final String tokenUsername = jwtService.extractUsername(token);

        return (userEntity.get().getUsername().equals(tokenUsername));
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
