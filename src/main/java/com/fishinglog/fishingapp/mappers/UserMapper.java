package com.fishinglog.fishingapp.mappers;

import com.fishinglog.fishingapp.domain.dto.persisted.UserDto;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between UserEntity and UserDto objects.
 *
 * @since 2024-02-10
 */
@Component
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    /**
     * Constructs a UserMapper with the necessary ModelMapper.
     *
     * @param modelMapper The ModelMapper used for object mapping operations.
     */
    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps a UserEntity to a UserDto. This mapping ensures that sensitive
     * information like passwords is not included in the UserDto.
     *
     * @param userEntity The UserEntity to map from.
     * @return The mapped UserDto with protected fields.
     */
    @Override
    public UserDto mapTo(UserEntity userEntity) {
        UserDto protectedUserDto = modelMapper.map(userEntity, UserDto.class);

        if (userEntity.getId() != null) {
            protectedUserDto.setId(userEntity.getId());
            protectedUserDto.setUsername(userEntity.getUsername());
            protectedUserDto.setPassword(null);
            protectedUserDto.setEmail(null);
        }
        return protectedUserDto;
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
