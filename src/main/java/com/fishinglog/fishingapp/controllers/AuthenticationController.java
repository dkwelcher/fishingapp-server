package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.auth.AuthenticationRequestDto;
import com.fishinglog.fishingapp.domain.auth.AuthenticationResponse;
import com.fishinglog.fishingapp.domain.auth.RegisterRequestDto;
import com.fishinglog.fishingapp.services.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related requests.
 *
 * @since 2024-10-15
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Registers a new user with the given request details.
     *
     * @param request The registration request containing user details.
     * @return A response entity containing the authentication response or a bad request error.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequestDto request) {

        return ResponseEntity.ok(service.register(request));
    }

    /**
     * Authenticates a user with the provided authentication request.
     *
     * @param request The authentication request containing login credentials.
     * @return A response entity containing the authentication response.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
