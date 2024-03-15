package com.fishinglog.fishingapp.services;

import com.fishinglog.fishingapp.domain.entities.TripEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TripService {

    TripEntity save(TripEntity tripEntity);

    List<TripEntity> findAll();

    Page<TripEntity> findAll(Pageable pageable);

    Optional<TripEntity> findOne(Long tripId);

    List<TripEntity> findByUserId(Long userId);

    List<TripEntity> findByUserIdAndDate(Long userId, LocalDate date);

    List<TripEntity> findLastSixMonthsByUserIdAndDate(Long userId);

    boolean isExists(Long tripId);

    TripEntity partialUpdate(Long tripId, TripEntity tripEntity);

    void delete(Long tripId);
}
