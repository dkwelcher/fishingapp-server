package com.fishinglog.fishingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration class that implements WebMvcConfigurer to provide custom web configurations,
 * such as CORS settings.
 *
 * @since 2024-02-10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mapping to allow cross-origin requests.
     *
     * This method sets up the CORS configuration for all paths (**)
     * and specifies allowed origins, HTTP methods, headers, and credentials.
     *
     * @param registry The CORS registry to which configurations are added.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
