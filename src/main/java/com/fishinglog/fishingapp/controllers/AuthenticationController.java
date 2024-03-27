package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.auth.AuthenticationRequest;
import com.fishinglog.fishingapp.domain.auth.AuthenticationResponse;
import com.fishinglog.fishingapp.domain.auth.RegisterRequest;
import com.fishinglog.fishingapp.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication-related requests.
 *
 * @since 2024-03-14
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
            @RequestBody RegisterRequest request) {

        if(!isRegisterRequestValid(request)) {
            return ResponseEntity.badRequest().build();
        }

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
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    private boolean isRegisterRequestValid(RegisterRequest registerRequest) {
        return isUsernameValid(registerRequest.getUsername()) &&
                isPasswordValid(registerRequest.getPassword()) &&
                isEmailValid(registerRequest.getEmail());
    }

    private boolean isUsernameValid(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (username.length() > 50) {
            return false;
        }

        // Letters and numbers only, no special characters or spaces
        String validUsernameRegex = "^[A-Za-z0-9]+$";

        return username.matches(validUsernameRegex);
    }

    private boolean isPasswordValid(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        int minLength = 6;
        int maxLength = 64;

        if (password.length() < minLength || password.length() > maxLength) {
            return false;
        }

        // Password must contain at least one letter, one digit, and one special character
        String validPasswordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[-!@#$%&*()_+=|<>?{}\\[\\]~]).{" + minLength + "," + maxLength + "}$";

        return password.matches(validPasswordRegex);
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        if (email.length() > 100) {
            return false;
        }

        // Basic email validation. Does not cover all edge cases.
        String validEmailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return email.matches(validEmailRegex);
    }


}
