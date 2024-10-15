package com.fishinglog.fishingapp.services.auth;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

/**
 * Interface for defining utilities user for handling JWT operations such as creation, validation, and extraction of information.
 *
 * @since 2024-10-15
 */
public interface JwtService {
    /**
     * Extracts the username from the JWT token.
     *
     * @param token The JWT token from which the username is extracted.
     * @return The username contained within the token.
     */
    String extractUsername(String token);

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token The JWT token from which the claim is extracted.
     * @param claimsResolver The function defining how to extract the claim.
     * @return The extracted claim.
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Generates a new JWT token for a user.
     *
     * @param userDetails The user details for which the token is generated.
     * @return The generated JWT token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a JWT token with specific claims.
     *
     * @param extractClaims The claims to include in the token.
     * @param userDetails The user details for which the token is generated.
     * @return The generated JWT token.
     */
    String generateToken(Map<String, Object> extractClaims, UserDetails userDetails);

    /**
     * Validates a JWT token against user details.
     *
     * @param token The JWT token to validate.
     * @param userDetails The user details against which the token is validated.
     * @return true if the token is valid, false otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
