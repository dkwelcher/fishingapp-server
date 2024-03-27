package com.fishinglog.fishingapp.repositories;

import com.fishinglog.fishingapp.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link UserEntity}, providing an abstraction layer to perform database operations related to users.
 *
 * @since 2024-02-19
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the {@link UserEntity} if found, or an empty Optional otherwise.
     */
    Optional<UserEntity> findByUsername(String username);

}
