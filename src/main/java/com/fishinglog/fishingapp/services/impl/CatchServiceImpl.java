package com.fishinglog.fishingapp.services.impl;

import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.repositories.CatchRepository;
import com.fishinglog.fishingapp.services.CatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CatchServiceImpl implements CatchService {

    private CatchRepository catchRepository;

    public CatchServiceImpl(CatchRepository catchRepository) {
        this.catchRepository = catchRepository;
    }

    @Override
    public CatchEntity save(CatchEntity catchEntity) {
        return catchRepository.save(catchEntity);
    }

    @Override
    public List<CatchEntity> findAll() {
        return StreamSupport
                .stream(catchRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList()
                );
    }

    @Override
    public Page<CatchEntity> findAll(Pageable pageable) {
        return catchRepository.findAll(pageable);
    }

    @Override
    public Optional<CatchEntity> findOne(Long catchId) {
        return catchRepository.findById(catchId);
    }

    @Override
    public boolean isExists(Long catchId) {
        return catchRepository.existsById(catchId);
    }

    @Override
    public CatchEntity partialUpdate(Long catchId, CatchEntity catchEntity) {
        catchEntity.setCatchId(catchId);

        return catchRepository.findById(catchId).map(existingCatch -> {
            Optional.ofNullable(catchEntity.getTime()).ifPresent(existingCatch::setTime);
            Optional.ofNullable(catchEntity.getLatitude()).ifPresent(existingCatch::setLatitude);
            Optional.ofNullable(catchEntity.getLongitude()).ifPresent(existingCatch::setLongitude);
            Optional.ofNullable(catchEntity.getSpecies()).ifPresent(existingCatch::setSpecies);
            Optional.ofNullable(catchEntity.getLureOrBait()).ifPresent(existingCatch::setLureOrBait);
            Optional.ofNullable(catchEntity.getWeatherCondition()).ifPresent(existingCatch::setWeatherCondition);
            Optional.ofNullable(catchEntity.getAirTemperature()).ifPresent(existingCatch::setAirTemperature);
            Optional.ofNullable(catchEntity.getWaterTemperature()).ifPresent(existingCatch::setWaterTemperature);
            Optional.ofNullable(catchEntity.getWindSpeed()).ifPresent(existingCatch::setWindSpeed);
            return catchRepository.save(existingCatch);
        }).orElseThrow(() -> new RuntimeException("Catch does not exist"));
    }

    @Override
    public void delete(Long catchId) {
        catchRepository.deleteById(catchId);
    }
}
