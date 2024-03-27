package com.fishinglog.fishingapp.config;

import com.fishinglog.fishingapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for the application's security and service beans.
 *
 * This class defines beans for user details service, authentication provider,
 * authentication manager, password encoder, and REST template. These beans are
 * used throughout the application to handle authentication, authorization,
 * password encoding, and HTTP client operations.
 *
 * @since 2024-03-16
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    /**
     * Creates a UserDetailsService bean to load user-specific data.
     *
     * @return UserDetailsService instance that loads user data from the UserRepository.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Creates an AuthenticationProvider bean to manage authentication.
     *
     * @return An AuthenticationProvider that uses a DAO-based approach for authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates an AuthenticationManager bean from the AuthenticationConfiguration.
     *
     * @param config The authentication configuration.
     * @return An AuthenticationManager instance.
     * @throws Exception If there's an issue creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates a PasswordEncoder bean that uses BCrypt hashing.
     *
     * @return A PasswordEncoder that uses BCrypt hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a RestTemplate bean for RESTful communication.
     *
     * @return An instance of RestTemplate for RESTful operations.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
