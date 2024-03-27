package com.fishinglog.fishingapp.mappers;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.dto.UserDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between CatchEntity and CatchDto objects.
 *
 * @since 2024-02-10
 */
@Component
public class CatchMapper implements Mapper<CatchEntity, CatchDto> {

    private final ModelMapper modelMapper;

    /**
     * Constructs a CatchMapper with the necessary ModelMapper.
     *
     * @param modelMapper The ModelMapper used for object mapping operations.
     */
    @Autowired
    public CatchMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps a CatchEntity to a CatchDto.
     *
     * @param catchEntity The CatchEntity to map from.
     * @return The mapped CatchDto.
     */
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

    /**
     * Maps a CatchDto back to a CatchEntity.
     *
     * @param catchDto The CatchDto to map from.
     * @return The mapped CatchEntity.
     */
    @Override
    public CatchEntity mapFrom(CatchDto catchDto) {
        return modelMapper.map(catchDto, CatchEntity.class);
    }
}
