package com.fishinglog.fishingapp.mappers;

import com.fishinglog.fishingapp.domain.dto.TripDto;
import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TripMapper implements Mapper<TripEntity, TripDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public TripMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

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

    @Override
    public TripEntity mapFrom(TripDto tripDto) {
        return modelMapper.map(tripDto, TripEntity.class);
    }
}


