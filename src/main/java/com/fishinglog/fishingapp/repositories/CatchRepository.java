package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for {@link CatchEntity} instances providing CRUD operations and additional
 * query methods to interact with the database layer.
 *
 * @since 2023-11-05
 */
@Repository
public interface CatchRepository extends CrudRepository<CatchEntity, Long>,
        PagingAndSortingRepository<CatchEntity, Long> {

    /**
     * Finds all CatchEntity instances associated with a given trip ID.
     *
     * @param tripId The ID of the trip.
     * @return A list of {@link CatchEntity} instances.
     */
    @Query("SELECT c FROM CatchEntity c WHERE c.trip.id = :tripId")
    List<CatchEntity> findByTripId(Long tripId);

    /**
     * Deletes all CatchEntity instances associated with a given trip ID.
     *
     * @param id The ID of the trip.
     */
    @Modifying
    @Query("DELETE FROM CatchEntity t WHERE t.trip.id = :id")
    void deleteByTripId(Long id);
}
