package com.fishinglog.fishingapp.mappers;

import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

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

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
