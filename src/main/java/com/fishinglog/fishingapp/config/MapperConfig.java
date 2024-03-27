package com.fishinglog.fishingapp.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ModelMapper to define bean configurations.
 *
 * @since 2023-10-31
 */
@Configuration
public class MapperConfig {

    /**
     * Creates a ModelMapper bean with a specified matching strategy.
     *
     * This bean is configured to use the LOOSE matching strategy, which provides
     * a more lenient mapping of source and destination properties.
     *
     * @return A configured ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
