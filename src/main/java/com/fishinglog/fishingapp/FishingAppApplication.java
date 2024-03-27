package com.fishinglog.fishingapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the FishingApp Spring Boot application.
 * Initializes the application and loads environment-specific variables.
 *
 * @since 2024-03-18
 */
@SpringBootApplication
public class FishingAppApplication {

	/**
	 * The main method that serves as the entry point for the Spring Boot application.
	 * It loads necessary environment variables and starts the application.
	 *
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String apiKey = dotenv.get("WEATHER_API_KEY");
		System.setProperty("api.key", apiKey);
		String jwtKey = dotenv.get("JWT_KEY");
		System.setProperty("jwt.key", jwtKey);

		SpringApplication.run(FishingAppApplication.class, args);
	}

}
