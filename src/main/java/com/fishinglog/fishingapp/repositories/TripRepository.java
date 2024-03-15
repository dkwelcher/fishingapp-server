package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.domain.entities.TripEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends CrudRepository<TripEntity, Long>,
        PagingAndSortingRepository<TripEntity, Long> {

    @Modifying
    @Query("DELETE FROM TripEntity t WHERE t.user.id = :id")
    void deleteByUserId(Long id);

    List<TripEntity> findByUserId(Long userId);

    List<TripEntity> findByUserIdAndDate(Long userId, LocalDate date);

    @Query("SELECT t FROM TripEntity t WHERE t.user.id = :userId AND t.date >= :startDate AND t.date <= :endDate")
    List<TripEntity> findLastSixMonthsByUserIdAndDate(Long userId, LocalDate startDate, LocalDate endDate);
}
