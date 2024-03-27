package com.fishinglog.fishingapp.services;

import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining the service operations for managing fishing catch entities.
 *
 * @since 2023-11-05
 */
public interface CatchService {

    /**
     * Saves a catch entity to the repository.
     *
     * @param catchEntity The catch entity to save.
     * @return The saved catch entity.
     */
    CatchEntity save(CatchEntity catchEntity);

    /**
     * Retrieves all catch entities.
     *
     * @return A list of all catch entities.
     */
    List<CatchEntity> findAll();

    /**
     * Retrieves a page of catch entities.
     *
     * @param pageable The pagination information.
     * @return A page of catch entities.
     */

    Page<CatchEntity> findAll(Pageable pageable);

    /**
     * Finds a single catch entity by its ID.
     *
     * @param catchId The ID of the catch entity to find.
     * @return An Optional containing the catch entity if found, or an empty Optional otherwise.
     */
    Optional<CatchEntity> findOne(Long catchId);

    /**
     * Finds all catch entities associated with a specific trip ID.
     *
     * @param tripId The ID of the trip.
     * @return A list of catch entities associated with the trip.
     */
    List<CatchEntity> findByTripId(Long tripId);

    /**
     * Checks if a catch entity exists by its ID.
     *
     * @param catchId The ID of the catch entity to check.
     * @return true if the entity exists, false otherwise.
     */
    boolean isExists(Long catchId);

    /**
     * Partially updates a catch entity.
     *
     * @param catchId The ID of the catch entity to update.
     * @param catchEntity The catch entity with updated fields.
     * @return The updated catch entity.
     */
    CatchEntity partialUpdate(Long catchId, CatchEntity catchEntity);

    /**
     * Deletes a catch entity by its ID.
     *
     * @param catchId The ID of the catch entity to delete.
     */
    void delete(Long catchId);
}
