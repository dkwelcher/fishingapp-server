package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.persisted.CatchDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import com.fishinglog.fishingapp.services.CatchService;
import com.fishinglog.fishingapp.services.auth.OwnershipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for handling requests related to fishing catches.
 *
 * @since 2024-10-15
 */
@RestController
@Log
public class CatchController {

    private final CatchService catchService;

    private final Mapper<CatchEntity, CatchDto> catchMapper;

    private final OwnershipService ownershipService;

    @Autowired
    public CatchController(CatchService catchService, Mapper<CatchEntity, CatchDto> catchMapper, OwnershipService ownershipService) {
        this.catchService = catchService;
        this.catchMapper = catchMapper;
        this.ownershipService = ownershipService;
    }

    /**
     * Creates a new fishing catch entry.
     *
     * @param userId The ID of the user creating the catch.
     * @param catchDto The data transfer object containing catch details.
     * @param request The HTTP request object.
     * @return A response entity with the created catch data or an error status.
     */
    // POST /catches?userId=123
    @PostMapping(path = "/catches")
    public ResponseEntity<CatchDto> createCatch(
            @RequestParam(value = "userId") Long userId,
            @Valid @RequestBody CatchDto catchDto,
            HttpServletRequest request) {

        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        CatchEntity catchEntity = catchMapper.mapFrom(catchDto);
        CatchEntity savedCatchEntity = catchService.save(catchEntity);
        CatchDto savedCatch = catchMapper.mapTo(savedCatchEntity);
        return new ResponseEntity<>(savedCatch, HttpStatus.CREATED);
    }

    /**
     * Updates an existing fishing catch entry.
     *
     * @param userId The ID of the user updating the catch.
     * @param catchId The ID of the catch to update.
     * @param catchDto The data transfer object containing the updated catch details.
     * @param request The HTTP request object.
     * @return A response entity with the updated catch data or an error status.
     */
    // PUT /catches/789?userId=123
    @PutMapping(path = "/catches/{catchId}")
    public ResponseEntity<CatchDto> updateCatch(
            @RequestParam(value = "userId") Long userId,
            @PathVariable Long catchId,
            @Valid @RequestBody CatchDto catchDto,
            HttpServletRequest request) {

        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!ownershipService.doesRequestUsernameMatchTokenUsername(userId, request)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(!catchService.isExists(catchId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CatchEntity catchEntity = catchMapper.mapFrom(catchDto);
        catchEntity.setCatchId(catchId);
        CatchEntity updatedCatchEntity = catchService.save(catchEntity);
        return new ResponseEntity<>(catchMapper.mapTo(updatedCatchEntity), HttpStatus.OK);
    }

    /**
     * Retrieves a list of catches associated with a specific trip.
     *
     * @param userId The ID of the user retrieving the catches.
     * @param tripId The ID of the trip associated with the catches.
     * @param request The HTTP request object.
     * @return A response entity with a list of catches or an error status.
     */
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

    /**
     * Deletes a specific fishing catch entry.
     *
     * @param userId The ID of the user deleting the catch.
     * @param catchId The ID of the catch to be deleted.
     * @param request The HTTP request object.
     * @return A response entity indicating the status of the operation.
     */
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
}
