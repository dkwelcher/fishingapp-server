package com.fishinglog.fishingapp.mappers.impl;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CatchMapperImpl implements Mapper<CatchEntity, CatchDto> {

    private ModelMapper modelMapper;

    public CatchMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CatchDto mapTo(CatchEntity catchEntity) {
        return modelMapper.map(catchEntity, CatchDto.class);
    }

    @Override
    public CatchEntity mapFrom(CatchDto catchDto) {
        return modelMapper.map(catchDto, CatchEntity.class);
    }
}
