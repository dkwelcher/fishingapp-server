package com.fishinglog.fishingapp.mappers;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatchMapper implements Mapper<CatchEntity, CatchDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public CatchMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CatchDto mapTo(CatchEntity catchEntity) {
        CatchDto catchDto = modelMapper.map(catchEntity, CatchDto.class);

        if(catchEntity.getTrip().getUser() != null) {
            UserDto userDto = new UserDto();
            userDto.setId(catchEntity.getTrip().getUser().getId());
            userDto.setUsername(catchEntity.getTrip().getUser().getUsername());
            userDto.setPassword(null);
            userDto.setEmail(null);

            catchDto.getTrip().setUser(userDto);
        }
        return catchDto;
    }

    @Override
    public CatchEntity mapFrom(CatchDto catchDto) {
        return modelMapper.map(catchDto, CatchEntity.class);
    }
}
