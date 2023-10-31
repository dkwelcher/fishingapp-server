package com.fishinglog.fishingapp.services.impl;

import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.repositories.CatchRepository;
import com.fishinglog.fishingapp.repositories.TripRepository;
import com.fishinglog.fishingapp.services.TripService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TripServiceImpl implements TripService {

    private TripRepository tripRepository;

    @Autowired
    private CatchRepository catchRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
    @Override
    public TripEntity save(TripEntity tripEntity) {
        return tripRepository.save(tripEntity);
    }

    @Override
    public List<TripEntity> findAll() {
        return StreamSupport
                .stream(tripRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList()
                );
    }

    @Override
    public Page<TripEntity> findAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    @Override
    public Optional<TripEntity> findOne(Long tripId) {
        return tripRepository.findById(tripId);
    }

    @Override
    public boolean isExists(Long tripId) {
        return tripRepository.existsById(tripId);
    }

    @Override
    public TripEntity partialUpdate(Long tripId, TripEntity tripEntity) {
        tripEntity.setTripId(tripId);

        return tripRepository.findById(tripId).map(existingTrip -> {
            Optional.ofNullable(tripEntity.getDate()).ifPresent(existingTrip::setDate);
            Optional.ofNullable(tripEntity.getBodyOfWater()).ifPresent(existingTrip::setBodyOfWater);
            return tripRepository.save(existingTrip);
        }).orElseThrow(() -> new RuntimeException("Trip does not exist"));
    }

    @Override
    @Transactional
    public void delete(Long tripId) {
        catchRepository.deleteByTripId(tripId);
        tripRepository.deleteById(tripId);
    }
}
