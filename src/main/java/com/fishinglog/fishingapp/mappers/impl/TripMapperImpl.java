package com.fishinglog.fishingapp.mappers.impl;

import com.fishinglog.fishingapp.domain.dto.persisted.TripDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Implementation of the Mapper interface for converting between TripEntity and TripDto objects.
 *
 * @since 2023-10-31
 */
@Component
public class TripMapperImpl implements Mapper<TripEntity, TripDto> {

    private ModelMapper modelMapper;

    /**
     * Constructs a TripMapperImpl with the necessary ModelMapper.
     *
     * @param modelMapper The ModelMapper used for object mapping operations.
     */
    public TripMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps a TripEntity to a TripDto.
     *
     * @param tripEntity The TripEntity to map from.
     * @return The mapped TripDto.
     */
    @Override
    public TripDto mapTo(TripEntity tripEntity) {
        return modelMapper.map(tripEntity, TripDto.class);
    }

    /**
     * Maps a TripDto back to a TripEntity.
     *
     * @param tripDto The TripDto to map from.
     * @return The mapped TripEntity.
     */
    @Override
    public TripEntity mapFrom(TripDto tripDto) {
        return modelMapper.map(tripDto, TripEntity.class);
    }
}
