package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatchRepository extends CrudRepository<CatchEntity, Long>,
        PagingAndSortingRepository<CatchEntity, Long> {

    @Modifying
    @Query("DELETE FROM CatchEntity t WHERE t.trip.id = :id")
    void deleteByTripId(Long id);
}
