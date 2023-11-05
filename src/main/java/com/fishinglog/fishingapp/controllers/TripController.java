package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.dto.TripDto;
import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import com.fishinglog.fishingapp.services.TripService;
import lombok.extern.java.Log;
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

    private TripService tripService;

    private Mapper<TripEntity, TripDto> tripMapper;

    public TripController(TripService tripService, Mapper<TripEntity, TripDto> tripMapper) {
        this.tripService = tripService;
        this.tripMapper = tripMapper;
    }

    @PostMapping(path = "/trips")
    public ResponseEntity<TripDto> createTrip(@RequestBody TripDto tripDto) {
        if(!isTripDtoValid(tripDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        TripEntity tripEntity = tripMapper.mapFrom(tripDto);
        TripEntity savedTripEntity = tripService.save(tripEntity);
        TripDto savedTrip = tripMapper.mapTo(savedTripEntity);
        return new ResponseEntity<>(savedTrip, HttpStatus.CREATED);
    }

    @PutMapping(path = "/trips/{tripId}")
    public ResponseEntity<TripDto> updateTrip(@PathVariable Long tripId, @RequestBody TripDto tripDto) {

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

    @PatchMapping(path = "/trips/{tripId}")
    public ResponseEntity<TripDto> partialUpdateTrip(
            @PathVariable("tripId") Long tripId,
            @RequestBody TripDto tripDto) {

        if(!isTripDtoValid(tripDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!tripService.isExists(tripId)) {
            return new ResponseEntity<>((HttpStatus.NOT_FOUND));
        }

        TripEntity tripEntity = tripMapper.mapFrom(tripDto);
        TripEntity updatedTripEntity = tripService.partialUpdate(tripId, tripEntity);
        return new ResponseEntity<>(
                tripMapper.mapTo(updatedTripEntity),
                HttpStatus.OK
        );
    }

    // GET /trips?id=123
    @GetMapping(path = "/trips")
    public ResponseEntity<List<TripDto>> listTrips(@RequestParam(value = "id") Long id) {
        if(id == null) {
            return new ResponseEntity<>((HttpStatus.BAD_REQUEST));
        }

        List<TripEntity> trips = tripService.findByUserId(id);
        List<TripDto> tripDtos = trips.stream()
                .map(tripMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tripDtos, HttpStatus.OK);
    }

    @GetMapping(path = "/trips/{tripId}")
    public ResponseEntity<TripDto> getTrip(@PathVariable("tripId") Long tripId) {
        Optional<TripEntity> foundTrip = tripService.findOne(tripId);
        return foundTrip.map(tripEntity -> {
            TripDto tripDto = tripMapper.mapTo(tripEntity);
            return new ResponseEntity<>(tripDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/trips/{tripId}")
    public ResponseEntity deleteTrip(@PathVariable("tripId") Long tripId) {
        tripService.delete(tripId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
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
