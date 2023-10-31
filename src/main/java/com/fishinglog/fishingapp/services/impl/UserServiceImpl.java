package com.fishinglog.fishingapp.services.impl;

import com.fishinglog.fishingapp.domain.entities.TripEntity;
import com.fishinglog.fishingapp.domain.entities.UserEntity;
import com.fishinglog.fishingapp.repositories.CatchRepository;
import com.fishinglog.fishingapp.repositories.TripRepository;
import com.fishinglog.fishingapp.repositories.UserRepository;
import com.fishinglog.fishingapp.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CatchRepository catchRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList()
                );
    }

    @Override
    public Optional<UserEntity> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity userEntity) {
        userEntity.setId(id);

        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        List<TripEntity> trips = tripRepository.findByUserId(id);
        for(TripEntity trip : trips) {
            catchRepository.deleteByTripId(trip.getTripId());
            tripRepository.deleteById(trip.getTripId());
        }

        userRepository.deleteById(id);
    }
}
