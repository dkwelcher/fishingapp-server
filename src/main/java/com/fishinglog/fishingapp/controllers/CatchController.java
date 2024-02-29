package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import com.fishinglog.fishingapp.services.CatchService;
import com.fishinglog.fishingapp.services.auth.OwnershipService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class CatchController {

    private final CatchService catchService;

    private final Mapper<CatchEntity, CatchDto> catchMapper;

    private final OwnershipService ownershipService;

    public CatchController(CatchService catchService, Mapper<CatchEntity, CatchDto> catchMapper, OwnershipService ownershipService) {
        this.catchService = catchService;
        this.catchMapper = catchMapper;
        this.ownershipService = ownershipService;
    }

    // POST /catches?userId=123
    @PostMapping(path = "/catches")
    public ResponseEntity<CatchDto> createCatch(
            @RequestParam(value = "userId") Long userId,
            @RequestBody CatchDto catchDto,
            HttpServletRequest request) {

        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(!isCatchDtoValid(catchDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CatchEntity catchEntity = catchMapper.mapFrom(catchDto);
        CatchEntity savedCatchEntity = catchService.save(catchEntity);
        CatchDto savedCatch = catchMapper.mapTo(savedCatchEntity);
        return new ResponseEntity<>(savedCatch, HttpStatus.CREATED);
    }

    // PUT /catches/789?userId=123
    @PutMapping(path = "/catches/{catchId}")
    public ResponseEntity<CatchDto> updateCatch(
            @RequestParam(value = "userId") Long userId,
            @PathVariable Long catchId,
            @RequestBody CatchDto catchDto,
            HttpServletRequest request) {

        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(!isCatchDtoValid(catchDto)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!catchService.isExists(catchId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CatchEntity catchEntity = catchMapper.mapFrom(catchDto);
        catchEntity.setCatchId(catchId);
        CatchEntity updatedCatchEntity = catchService.save(catchEntity);
        return new ResponseEntity<>(catchMapper.mapTo(updatedCatchEntity), HttpStatus.OK);
    }

    // GET /catches?userId=123&tripId=789
    @GetMapping(path = "/catches")
    public ResponseEntity<List<CatchDto>> listCatches(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "tripId") Long tripId,
            HttpServletRequest request) {

        if(tripId == null || userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<CatchEntity> catches = catchService.findByTripId(tripId);
        List<CatchDto> catchDtos = catches.stream()
                .map(catchMapper::mapTo)
                .collect(Collectors.toList());

        return new ResponseEntity<>(catchDtos, HttpStatus.OK);
    }

    // DELETE /catches/789?userId=123
    @DeleteMapping(path = "/catches/{catchId}")
    public ResponseEntity<HttpStatus> deleteTrip(
            @RequestParam(value = "userId") Long userId,
            @PathVariable("catchId") Long catchId,
            HttpServletRequest request) {

        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        catchService.delete(catchId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean isCatchDtoValid(CatchDto catchDto) {
        return isTimeValid(catchDto.getTime()) &&
                isCoordinatesValid(catchDto.getLatitude(), catchDto.getLongitude());
    }

    private boolean isTimeValid(LocalTime time) {
        return time != null;
    }

    private boolean isCoordinatesValid(Double latitude, Double longitude) {
        if(latitude == null || longitude == null) {
            return false;
        }

        boolean isLatitudeValid = false;
        boolean isLongitudeValid = false;

        if(latitude >= -90 && latitude <= 90) {
            isLatitudeValid = true;
        }

        if(longitude >= -180 && longitude <= 180) {
            isLongitudeValid = true;
        }

        return isLatitudeValid && isLongitudeValid;
    }
}
