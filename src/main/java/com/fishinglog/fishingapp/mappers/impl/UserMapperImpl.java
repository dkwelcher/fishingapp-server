package com.fishinglog.fishingapp.mappers.impl;

import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Implementation of the Mapper interface for converting between UserEntity and UserDto objects.
 *
 * @since 2023-10-31
 */
@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {

    private ModelMapper modelMapper;

    /**
     * Constructs a UserMapperImpl with the necessary ModelMapper.
     *
     * @param modelMapper The ModelMapper used for object mapping operations.
     */
    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps a UserEntity to a UserDto.
     *
     * @param userEntity The UserEntity to map from.
     * @return The mapped UserDto.
     */
    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    /**
     * Maps a UserDto back to a UserEntity.
     *
     * @param userDto The UserDto to map from.
     * @return The mapped UserEntity.
     */
    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
