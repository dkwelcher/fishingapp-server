package com.fishinglog.fishingapp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FishingAppApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String apiKey = dotenv.get("WEATHER_API_KEY");
		System.setProperty("api.key", apiKey);
		String jwtKey = dotenv.get("JWT_KEY");
		System.setProperty("jwt.key", jwtKey);

		SpringApplication.run(FishingAppApplication.class, args);
	}

}
