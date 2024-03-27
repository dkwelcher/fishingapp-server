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

/**
 * Service implementation for managing user entities.
 *
 * @since 2024-02-19
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CatchRepository catchRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Saves a user entity.
     *
     * @param userEntity The user entity to save.
     * @return The saved user entity.
     */
    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    /**
     * Retrieves all user entities.
     *
     * @return A list of all user entities.
     */
    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList()
                );
    }

    /**
     * Finds a user entity by its ID.
     *
     * @param id The ID of the user entity to find.
     * @return An Optional containing the found user entity or an empty Optional if not found.
     */
    @Override
    public Optional<UserEntity> findOne(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Finds a user entity by username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the found user entity or an empty Optional if not found.
     */
    public Optional<UserEntity> findByUsername(String username) { return userRepository.findByUsername(username);}

    /**
     * Checks if a user entity exists by its ID.
     *
     * @param id The ID of the user entity to check.
     * @return True if the entity exists, false otherwise.
     */
    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

    /**
     * Performs a partial update on a user entity.
     *
     * @param id The ID of the user entity to update.
     * @param userEntity The user entity with updated fields.
     * @return The updated user entity.
     */
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

    /**
     * Deletes a user entity and its associated trips and catches by the user's ID.
     *
     * @param id The ID of the user entity to delete.
     */
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
