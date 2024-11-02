package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * Creates a new {@link User} entity.
     *
     * @param user the {@link User} object to be created
     * @return the newly created {@link User} object with assigned ID and persisted state
     */
    User createUser(User user);

    /**
     * Deletes an existing {@link User} entity by its unique identifier.
     *
     * @param userId the unique identifier of the {@link User} to be deleted
     */
    void deleteUser(long userId);

    /**
     * Updates an existing {@link User} entity with new values.
     *
     * @param user the {@link User} object containing updated data
     * @return the updated {@link User} object after persistence
     */
    User updateUser(User user);
}
