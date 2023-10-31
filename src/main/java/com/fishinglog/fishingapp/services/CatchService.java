package com.fishinglog.fishingapp.services;

import com.fishinglog.fishingapp.domain.entities.CatchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CatchService {

    CatchEntity save(CatchEntity catchEntity);

    List<CatchEntity> findAll();

    Page<CatchEntity> findAll(Pageable pageable);

    Optional<CatchEntity> findOne(Long catchId);

    boolean isExists(Long catchId);

    CatchEntity partialUpdate(Long catchId, CatchEntity catchEntity);

    void delete(Long catchId);
}
