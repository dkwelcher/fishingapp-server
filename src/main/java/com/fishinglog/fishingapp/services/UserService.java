package com.fishinglog.fishingapp.services;

import com.fishinglog.fishingapp.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface defining operations for managing user entities.
 *
 * @since 2024-02-19
 */
public interface UserService {

    /**
     * Saves a user entity.
     *
     * @param userEntity The user entity to be saved.
     * @return The saved user entity.
     */
    UserEntity save(UserEntity userEntity);

    /**
     * Retrieves all user entities.
     *
     * @return A list of all user entities.
     */
    List<UserEntity> findAll();

    /**
     * Finds a user entity by its ID.
     *
     * @param id The ID of the user entity.
     * @return An Optional containing the found user entity or an empty Optional if not found.
     */
    Optional<UserEntity> findOne(Long id);

    /**
     * Finds a user entity by its username.
     *
     * @param username The username of the user entity.
     * @return An Optional containing the found user entity or an empty Optional if not found.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Checks if a user entity exists by its ID.
     *
     * @param id The ID of the user entity to check.
     * @return True if the entity exists, false otherwise.
     */
    boolean isExists(Long id);

    /**
     * Performs a partial update on a user entity.
     *
     * @param id The ID of the user entity to update.
     * @param userEntity The user entity with updated fields.
     * @return The updated user entity.
     */
    UserEntity partialUpdate(Long id, UserEntity userEntity);

    /**
     * Deletes a user entity by its ID.
     *
     * @param id The ID of the user entity to delete.
     */
    void delete(Long id);
}
