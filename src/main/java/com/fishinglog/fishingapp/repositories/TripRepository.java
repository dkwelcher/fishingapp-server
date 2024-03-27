package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.domain.entities.TripEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for {@link TripEntity}, providing an abstraction layer to perform various database operations.
 *
 * @since 2024-03-15
 */
@Repository
public interface TripRepository extends CrudRepository<TripEntity, Long>,
        PagingAndSortingRepository<TripEntity, Long> {

    /**
     * Deletes all trip records associated with a given user ID.
     *
     * @param id The ID of the user whose trips are to be deleted.
     */
    @Modifying
    @Query("DELETE FROM TripEntity t WHERE t.user.id = :id")
    void deleteByUserId(Long id);

    /**
     * Finds all trips associated with a given user ID.
     *
     * @param userId The ID of the user whose trips are to be found.
     * @return A list of {@link TripEntity}.
     */
    List<TripEntity> findByUserId(Long userId);

    /**
     * Finds all trips for a user on a specific date.
     *
     * @param userId The user ID.
     * @param date The date of the trip.
     * @return A list of {@link TripEntity} for the specified date.
     */
    List<TripEntity> findByUserIdAndDate(Long userId, LocalDate date);

    /**
     * Finds trips for a user within the last six months.
     *
     * @param userId The user ID.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return A list of {@link TripEntity} within the last six months.
     */
    @Query("SELECT t FROM TripEntity t WHERE t.user.id = :userId AND t.date >= :startDate AND t.date <= :endDate")
    List<TripEntity> findLastSixMonthsByUserIdAndDate(Long userId, LocalDate startDate, LocalDate endDate);
}
