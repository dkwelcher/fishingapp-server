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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of the TripService interface, handling business logic for trip-related operations.
 *
 * @since 2024-03-15
 */
@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Autowired
    private CatchRepository catchRepository;

    /**
     * Constructs a TripServiceImpl with the necessary trip repository.
     *
     * @param tripRepository The repository used for trip entity persistence.
     */
    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /**
     * Saves a trip entity to the database.
     *
     * @param tripEntity The trip entity to be saved.
     * @return The saved trip entity.
     */
    @Override
    public TripEntity save(TripEntity tripEntity) {
        return tripRepository.save(tripEntity);
    }

    /**
     * Retrieves all trip entities from the database.
     *
     * @return A list of all trip entities.
     */
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

    /**
     * Retrieves all trip entities with pagination support.
     *
     * @param pageable Pagination details.
     * @return A page of trip entities.
     */
    @Override
    public Page<TripEntity> findAll(Pageable pageable) {
        return tripRepository.findAll(pageable);
    }

    /**
     * Finds a single trip entity by its ID.
     *
     * @param tripId The ID of the trip to find.
     * @return An Optional containing the found trip entity or an empty Optional if no trip is found.
     */
    @Override
    public Optional<TripEntity> findOne(Long tripId) {
        return tripRepository.findById(tripId);
    }

    /**
     * Finds all trip entities associated with a given user ID.
     *
     * @param userId The ID of the user.
     * @return A list of trip entities associated with the user.
     */
    @Override
    public List<TripEntity> findByUserId(Long userId) { return tripRepository.findByUserId(userId); }

    /**
     * Finds all trips for a specific user on a given date.
     *
     * @param userId The user ID.
     * @param date The date for which to find trips.
     * @return A list of trip entities matching the criteria.
     */
    public List<TripEntity> findByUserIdAndDate(Long userId, LocalDate date) { return tripRepository.findByUserIdAndDate(userId, date); }

    /**
     * Finds trips for a user within the last six months.
     *
     * @param userId The user ID.
     * @return A list of trip entities within the last six months.
     */
    public List<TripEntity> findLastSixMonthsByUserIdAndDate(Long userId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(6).plusDays(1);
        return tripRepository.findLastSixMonthsByUserIdAndDate(userId, startDate, endDate);
    }

    /**
     * Checks whether a trip entity exists in the database.
     *
     * @param tripId The ID of the trip to check.
     * @return True if the trip exists, false otherwise.
     */
    @Override
    public boolean isExists(Long tripId) {
        return tripRepository.existsById(tripId);
    }

    /**
     * Performs a partial update on a trip entity.
     *
     * @param tripId The ID of the trip to update.
     * @param tripEntity The trip entity with updated fields.
     * @return The updated trip entity.
     */
    @Override
    public TripEntity partialUpdate(Long tripId, TripEntity tripEntity) {
        tripEntity.setTripId(tripId);

        return tripRepository.findById(tripId).map(existingTrip -> {
            Optional.ofNullable(tripEntity.getDate()).ifPresent(existingTrip::setDate);
            Optional.ofNullable(tripEntity.getBodyOfWater()).ifPresent(existingTrip::setBodyOfWater);
            return tripRepository.save(existingTrip);
        }).orElseThrow(() -> new RuntimeException("Trip does not exist"));
    }

    /**
     * Deletes a trip entity and its associated catches from the database.
     *
     * @param tripId The ID of the trip to delete.
     */
    @Override
    @Transactional
    public void delete(Long tripId) {
        catchRepository.deleteByTripId(tripId);
        tripRepository.deleteById(tripId);
    }
}
