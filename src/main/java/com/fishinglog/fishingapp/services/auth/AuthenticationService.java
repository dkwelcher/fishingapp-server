package com.fishinglog.fishingapp.services.auth;

import com.fishinglog.fishingapp.domain.Role;
import com.fishinglog.fishingapp.domain.auth.AuthenticationRequestDto;
import com.fishinglog.fishingapp.domain.auth.AuthenticationResponse;
import com.fishinglog.fishingapp.domain.auth.RegisterRequestDto;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication and registration logic.
 *
 * @since 2024-10-15
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user with the provided credentials.
     *
     * @param request The registration request containing the user's credentials.
     * @return An {@link AuthenticationResponse} containing the new user's JWT token.
     */
    public AuthenticationResponse register(RegisterRequestDto request) {
        var user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode((request.getPassword())))
                .email(request.getEmail())
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param request The authentication request containing the user's credentials.
     * @return An {@link AuthenticationResponse} containing the user's JWT token along with user details.
     */
    public AuthenticationResponse authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(); // should catch and handle exception (not done here)
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }
}