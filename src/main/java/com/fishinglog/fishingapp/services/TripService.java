package com.fishinglog.fishingapp.services;

import com.fishinglog.fishingapp.domain.entities.TripEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface defining operations for managing trip entities.
 *
 * @since 2024-03-15
 */
public interface TripService {

    /**
     * Saves a trip entity.
     *
     * @param tripEntity The trip entity to save.
     * @return The saved trip entity.
     */
    TripEntity save(TripEntity tripEntity);

    /**
     * Retrieves all trip entities.
     *
     * @return A list of all trip entities.
     */
    List<TripEntity> findAll();

    /**
     * Retrieves all trip entities with pagination.
     *
     * @param pageable Pagination information.
     * @return A page of trip entities.
     */
    Page<TripEntity> findAll(Pageable pageable);

    /**
     * Finds a trip entity by its ID.
     *
     * @param tripId The ID of the trip entity.
     * @return An Optional containing the found trip entity or an empty Optional if not found.
     */
    Optional<TripEntity> findOne(Long tripId);

    /**
     * Finds trip entities associated with a specific user ID.
     *
     * @param userId The ID of the user.
     * @return A list of trip entities associated with the user.
     */
    List<TripEntity> findByUserId(Long userId);

    /**
     * Finds trip entities for a user on a specific date.
     *
     * @param userId The user ID.
     * @param date The date for which trips are retrieved.
     * @return A list of trip entities for the specified date.
     */
    List<TripEntity> findByUserIdAndDate(Long userId, LocalDate date);

    /**
     * Finds trip entities for a user within the last six months.
     *
     * @param userId The user ID.
     * @return A list of trip entities for the last six months.
     */
    List<TripEntity> findLastSixMonthsByUserIdAndDate(Long userId);

    /**
     * Checks if a trip entity exists by its ID.
     *
     * @param tripId The ID of the trip entity to check.
     * @return True if the entity exists, false otherwise.
     */
    boolean isExists(Long tripId);

    /**
     * Performs a partial update on a trip entity.
     *
     * @param tripId The ID of the trip entity to update.
     * @param tripEntity The trip entity with updated fields.
     * @return The updated trip entity.
     */
    TripEntity partialUpdate(Long tripId, TripEntity tripEntity);

    /**
     * Deletes a trip entity by its ID.
     *
     * @param tripId The ID of the trip entity to delete.
     */
    void delete(Long tripId);
}
