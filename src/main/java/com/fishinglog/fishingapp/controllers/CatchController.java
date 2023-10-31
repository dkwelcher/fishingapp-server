package com.fishinglog.fishingapp.controllers;

import com.fishinglog.fishingapp.domain.dto.CatchDto;
import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import com.fishinglog.fishingapp.mappers.Mapper;
import com.fishinglog.fishingapp.services.CatchService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
public class CatchController {

    private CatchService catchService;

    private Mapper<CatchEntity, CatchDto> catchMapper;

    public CatchController(CatchService catchService, Mapper<CatchEntity, CatchDto> catchMapper) {
        this.catchService = catchService;
        this.catchMapper = catchMapper;
    }

    @PostMapping(path = "/catches")
    public ResponseEntity<CatchDto> createCatch(@RequestBody CatchDto catchDto) {
        CatchEntity catchEntity = catchMapper.mapFrom(catchDto);
        CatchEntity savedCatchEntity = catchService.save(catchEntity);
        CatchDto savedCatch = catchMapper.mapTo(savedCatchEntity);
        return new ResponseEntity<>(savedCatch, HttpStatus.CREATED);
    }

    @PutMapping(path = "/catches/{catchId}")
    public ResponseEntity<CatchDto> updateCatch(@PathVariable Long catchId, @RequestBody CatchDto catchDto) {

        if(!catchService.isExists(catchId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CatchEntity catchEntity = catchMapper.mapFrom(catchDto);
        catchEntity.setCatchId(catchId);
        CatchEntity updatedCatchEntity = catchService.save(catchEntity);
        return new ResponseEntity<>(catchMapper.mapTo(updatedCatchEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/catches/{catchId}")
    public ResponseEntity<CatchDto> partialUpdateTrip(
            @PathVariable("catchId") Long catchId,
            @RequestBody CatchDto catchDto) {

        if(!catchService.isExists(catchId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CatchEntity catchEntity = catchMapper.mapFrom(catchDto);
        CatchEntity updatedCatchEntity = catchService.partialUpdate(catchId, catchEntity);
        return new ResponseEntity<>(
                catchMapper.mapTo(updatedCatchEntity),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "/catches")
    public List<CatchDto> listCatches() {
        List<CatchEntity> catches = catchService.findAll();
        return catches.stream()
                .map(catchMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/catches/{catchId}")
    public ResponseEntity<CatchDto> getCatch(@PathVariable("catchId") Long catchId) {
        Optional<CatchEntity> foundCatch = catchService.findOne(catchId);
        return foundCatch.map(catchEntity -> {
            CatchDto catchDto = catchMapper.mapTo(catchEntity);
            return new ResponseEntity<>(catchDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/catches/{catchId}")
    public ResponseEntity deleteTrip(@PathVariable("catchId") Long catchId) {
        catchService.delete(catchId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
