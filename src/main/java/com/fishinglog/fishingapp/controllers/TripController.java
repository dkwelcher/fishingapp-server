package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.persisted.TripDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import com.fishinglog.fishingapp.services.TripService;
import com.fishinglog.fishingapp.services.auth.OwnershipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for handling trip-related operations.
 *
 * @since 2024-10-15
 */
@RestController
@Log
public class TripController {

    private final TripService tripService;

    private final Mapper<TripEntity, TripDto> tripMapper;

    private final OwnershipService ownershipService;

    @Autowired
    public TripController(TripService tripService, Mapper<TripEntity, TripDto> tripMapper, OwnershipService ownershipService) {
        this.tripService = tripService;
        this.tripMapper = tripMapper;
        this.ownershipService = ownershipService;
    }

    /**
     * Creates a new trip record.
     *
     * @param userId The ID of the user creating the trip.
     * @param tripDto Data transfer object representing the trip to be created.
     * @param request The HTTP request object.
     * @return A ResponseEntity containing the created trip or an error status.
     */
    // POST /trips?userId=123
    @PostMapping(path = "/trips")
    public ResponseEntity<TripDto> createTrip(
            @RequestParam(value = "userId") Long userId,
            @Valid @RequestBody TripDto tripDto,
            HttpServletRequest request) {

        if(!userId.equals(tripDto.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        TripEntity tripEntity = tripMapper.mapFrom(tripDto);
        TripEntity savedTripEntity = tripService.save(tripEntity);
        TripDto savedTrip = tripMapper.mapTo(savedTripEntity);
        return new ResponseEntity<>(savedTrip, HttpStatus.CREATED);
    }

    /**
     * Updates an existing trip record.
     *
     * @param userId The ID of the user updating the trip.
     * @param tripId The ID of the trip to update.
     * @param tripDto Data transfer object representing the updated trip details.
     * @param request The HTTP request object.
     * @return A ResponseEntity containing the updated trip or an error status.
     */
    // PUT /trips/789?userId=123
    @PutMapping(path = "/trips/{tripId}")
    public ResponseEntity<TripDto> updateTrip(
            @RequestParam(value = "userId") Long userId,
            @PathVariable Long tripId,
            @Valid @RequestBody TripDto tripDto,
            HttpServletRequest request) {

        if(!userId.equals(tripDto.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(!tripService.isExists(tripId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TripEntity tripEntity = tripMapper.mapFrom(tripDto);
        tripEntity.setTripId(tripId);
        TripEntity updatedTripEntity = tripService.save(tripEntity);
        return new ResponseEntity<>(tripMapper.mapTo(updatedTripEntity), HttpStatus.OK);
    }

    /**
     * Retrieves a list of trips for a user on a specific date.
     *
     * @param userId The ID of the user whose trips are being queried.
     * @param date The date for which trips are being retrieved.
     * @param request The HTTP request object.
     * @return A ResponseEntity containing a list of TripDto objects or an error status.
     */
    // GET /trips?userId=123&date=2024-01-01
    @GetMapping(path = "/trips")
    public ResponseEntity<List<TripDto>> listTripsByUserIdAndDate(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            HttpServletRequest request) {

        if (userId == null || date == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TripEntity> trips;
        trips = tripService.findByUserIdAndDate(userId, date);

        List<TripDto> tripDtos = trips.stream()
                .map(tripMapper::mapTo)
                .collect(Collectors.toList());

        return new ResponseEntity<>(tripDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a list of trips for a user over the last six months.
     *
     * @param userId The ID of the user whose trips are being queried.
     * @param request The HTTP request object.
     * @return A ResponseEntity containing a list of TripDto objects or an error status.
     */
    // GET /trips/sixMonths?userId=123
    @GetMapping(path = "/trips/sixMonths")
    public ResponseEntity<List<TripDto>> listTripsLastSixMonthsByUserId(
            @RequestParam(value = "userId") Long userId,
            HttpServletRequest request) {

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<TripEntity> trips;
        trips = tripService.findLastSixMonthsByUserIdAndDate(userId);

        List<TripDto> tripDtos = trips.stream()
                .map(tripMapper::mapTo)
                .collect(Collectors.toList());

        return new ResponseEntity<>(tripDtos, HttpStatus.OK);
    }

    /**
     * Deletes a trip record.
     *
     * @param userId The ID of the user deleting the trip.
     * @param tripId The ID of the trip to be deleted.
     * @param request The HTTP request object.
     * @return A ResponseEntity indicating the result of the deletion operation.
     */
    // DELETE /trips/789?userId=123
    @DeleteMapping(path = "/trips/{tripId}")
    public ResponseEntity<HttpStatus> deleteTrip(
            @RequestParam(value = "userId") Long userId,
            @PathVariable("tripId") Long tripId,
            HttpServletRequest request) {

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        tripService.delete(tripId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
