package com.fishinglog.fishingapp.mappers.impl;

import com.fishinglog.fishingapp.domain.dto.persisted.TripDto;
import com.fishinglog.fishingapp.domain.dto.persisted.UserDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between TripEntity and TripDto objects.
 *
 * @since 2024-02-10
 */
@Component
public class TripMapperImpl implements Mapper<TripEntity, TripDto> {

    private final ModelMapper modelMapper;

    /**
     * Constructs a TripMapper with the necessary ModelMapper.
     *
     * @param modelMapper The ModelMapper used for object mapping operations.
     */
    @Autowired
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
        TripDto tripDto = modelMapper.map(tripEntity, TripDto.class);

        if (tripEntity.getUser() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(tripEntity.getUser().getId());
            userDto.setUsername(tripEntity.getUser().getUsername());
            userDto.setPassword(null);
            userDto.setEmail(null);

            tripDto.setUser(userDto);
        }
        return tripDto;
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


