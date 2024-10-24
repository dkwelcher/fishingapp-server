package com.fishinglog.fishingapp.services.auth.impl;

import com.fishinglog.fishingapp.domain.Role;
import com.fishinglog.fishingapp.domain.dto.auth.AuthenticationRequestDto;
import com.fishinglog.fishingapp.domain.dto.auth.AuthenticationResponseDto;
import com.fishinglog.fishingapp.domain.dto.auth.RegisterRequestDto;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.repositories.UserRepository;
import com.fishinglog.fishingapp.services.auth.AuthenticationService;
import com.fishinglog.fishingapp.services.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication and registration logic.
 * See {@link AuthenticationService}.
 *
 * @since 2024-10-15
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDto register(RegisterRequestDto request) {
        var user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode((request.getPassword())))
                .email(request.getEmail())
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(); // TODO: should catch and handle exception
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }
}