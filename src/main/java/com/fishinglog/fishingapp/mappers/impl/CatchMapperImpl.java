package com.fishinglog.fishingapp.mappers.impl;

import com.fishinglog.fishingapp.domain.dto.persisted.CatchDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Implementation of the Mapper interface for converting between CatchEntity and CatchDto objects.
 *
 * @since 2023-10-31
 */
@Component
public class CatchMapperImpl implements Mapper<CatchEntity, CatchDto> {

    private ModelMapper modelMapper;

    /**
     * Constructs a CatchMapperImpl with the necessary ModelMapper.
     *
     * @param modelMapper The ModelMapper used for object mapping operations.
     */
    public CatchMapperImpl(ModelMapper modelMapper) {
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
        return modelMapper.map(catchEntity, CatchDto.class);
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
