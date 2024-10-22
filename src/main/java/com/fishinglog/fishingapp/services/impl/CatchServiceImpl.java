package com.fishinglog.fishingapp.services.impl;

import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.repositories.CatchRepository;
import com.fishinglog.fishingapp.services.CatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service implementation for managing catch entities.
 *
 * @since 2023-11-05
 */
@Service
public class CatchServiceImpl implements CatchService {

    private final CatchRepository catchRepository;

    /**
     * Constructs a service instance with the necessary catch repository.
     *
     * @param catchRepository The repository used for catch entity persistence.
     */
    @Autowired
    public CatchServiceImpl(CatchRepository catchRepository) {
        this.catchRepository = catchRepository;
    }

    /**
     * Saves a catch entity.
     *
     * @param catchEntity The catch entity to save.
     * @return The saved catch entity.
     */
    @Override
    public CatchEntity save(CatchEntity catchEntity) {
        return catchRepository.save(catchEntity);
    }

    /**
     * Retrieves all catch entities.
     *
     * @return A list of all catch entities.
     */
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

    /**
     * Retrieves all catch entities with pagination.
     *
     * @param pageable Pagination information.
     * @return A page of catch entities.
     */
    @Override
    public Page<CatchEntity> findAll(Pageable pageable) {
        return catchRepository.findAll(pageable);
    }

    /**
     * Finds a catch entity by its ID.
     *
     * @param catchId The ID of the catch entity to find.
     * @return An Optional containing the found catch entity or an empty Optional if not found.
     */
    @Override
    public Optional<CatchEntity> findOne(Long catchId) {
        return catchRepository.findById(catchId);
    }

    /**
     * Finds catch entities associated with a specific trip ID.
     *
     * @param tripId The ID of the trip associated with the catches.
     * @return A list of catch entities for the specified trip.
     */
    @Override
    public List<CatchEntity> findByTripId(Long tripId) { return catchRepository.findByTripId(tripId); }

    /**
     * Checks if a catch entity exists by its ID.
     *
     * @param catchId The ID of the catch entity to check.
     * @return True if the entity exists, false otherwise.
     */
    @Override
    public boolean isExists(Long catchId) {
        return catchRepository.existsById(catchId);
    }

    /**
     * Performs a partial update on a catch entity.
     *
     * @param catchId The ID of the catch entity to update.
     * @param catchEntity The catch entity with updated fields.
     * @return The updated catch entity.
     */
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

    /**
     * Deletes a catch entity by its ID.
     *
     * @param catchId The ID of the catch entity to delete.
     */
    @Override
    public void delete(Long catchId) {
        catchRepository.deleteById(catchId);
    }
}
