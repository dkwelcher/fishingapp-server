package com.fishinglog.fishingapp.mappers.impl;

import com.fishinglog.fishingapp.domain.dto.TripDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TripMapperImpl implements Mapper<TripEntity, TripDto> {

    private ModelMapper modelMapper;

    public TripMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public TripDto mapTo(TripEntity tripEntity) {
        return modelMapper.map(tripEntity, TripDto.class);
    }

    @Override
    public TripEntity mapFrom(TripDto tripDto) {
        return modelMapper.map(tripDto, TripEntity.class);
    }
}
