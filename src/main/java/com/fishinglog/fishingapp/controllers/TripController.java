package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.TripDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import com.fishinglog.fishingapp.services.TripService;
import com.fishinglog.fishingapp.services.auth.OwnershipService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class TripController {

    private final TripService tripService;

    private final Mapper<TripEntity, TripDto> tripMapper;

    private final OwnershipService ownershipService;

    public TripController(TripService tripService, Mapper<TripEntity, TripDto> tripMapper, OwnershipService ownershipService) {
        this.tripService = tripService;
        this.tripMapper = tripMapper;
        this.ownershipService = ownershipService;
    }

    // POST /trips?userId=123
    @PostMapping(path = "/trips")
    public ResponseEntity<TripDto> createTrip(
            @RequestParam(value = "userId") Long userId,
            @RequestBody TripDto tripDto,
            HttpServletRequest request) {

        if(!userId.equals(tripDto.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(!isTripDtoValid(tripDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TripEntity tripEntity = tripMapper.mapFrom(tripDto);
        TripEntity savedTripEntity = tripService.save(tripEntity);
        TripDto savedTrip = tripMapper.mapTo(savedTripEntity);
        return new ResponseEntity<>(savedTrip, HttpStatus.CREATED);
    }

    // PUT /trips/789?userId=123
    @PutMapping(path = "/trips/{tripId}")
    public ResponseEntity<TripDto> updateTrip(
            @RequestParam(value = "userId") Long userId,
            @PathVariable Long tripId,
            @RequestBody TripDto tripDto,
            HttpServletRequest request) {

        if(!userId.equals(tripDto.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(!isTripDtoValid(tripDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!tripService.isExists(tripId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TripEntity tripEntity = tripMapper.mapFrom(tripDto);
        tripEntity.setTripId(tripId);
        TripEntity updatedTripEntity = tripService.save(tripEntity);
        return new ResponseEntity<>(tripMapper.mapTo(updatedTripEntity), HttpStatus.OK);
    }

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

    private boolean isTripDtoValid(TripDto trip) {
        return isDateValid(trip.getDate()) &&
                isBodyOfWaterValid(trip.getBodyOfWater());
    }

    private boolean isDateValid(LocalDate date) {
        return date != null;
    }

    private boolean isBodyOfWaterValid(String bodyOfWater) {
        if(bodyOfWater == null || bodyOfWater.trim().isEmpty()) {
            return false;
        }

        int minLength = 1;
        int maxLength = 100;

        String regex = "^[A-Za-z0-9 ]{" + minLength + "," + maxLength + "}$";
        return bodyOfWater.matches(regex);
    }
}
