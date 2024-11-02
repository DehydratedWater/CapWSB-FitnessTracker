package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Creates a new {@link User} in the system.
     *
     * @param user the {@link User} entity to be created
     * @return the created {@link User} with an assigned ID
     * @throws IllegalArgumentException if the user already has an ID, indicating it exists in the database
     */
    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    /**
     * Deletes an existing {@link User} based on the provided user ID.
     *
     * @param userId the ID of the user to be deleted
     */
    @Override
    public void deleteUser(long userId) {
        log.info("Deleting User with id: {}", userId);

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            userRepository.delete(user.get());
            log.info("Successfully deleted user with id: {}", userId);
        } else {
            log.info("Failed to delete user with id: {}", userId);
        }
    }

    /**
     * Retrieves detailed information about a user based on their ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return an {@link Optional} containing the {@link User} details, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUserDetails(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            log.info("User with id:{} was found.", userId);
        } else {
            log.info("User with id:{} could not be found.", userId);
        }
        return user;
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user to be retrieved
     * @return an {@link Optional} containing the {@link User}, or {@link Optional#empty()} if no user is found
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            log.info("User with email:{} was found.", email);
        } else {
            log.info("User with email:{} could not be found.", email);
        }
        return user;
    }

    /**
     * Retrieves all users who are older than a specified date.
     *
     * @param time the date to compare each user's birthdate against
     * @return a {@link List} of {@link User} entities who are older than the specified date
     */
    @Override
    public List<User> findUsersOlderThenDate(LocalDate time) {
        return userRepository.findUsersOlderThenDate(time);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return an {@link Optional} containing the {@link User}, or {@link Optional#empty()} if not found
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a {@link List} of all {@link User} entities
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates an existing {@link User} in the system.
     *
     * @param user the {@link User} entity to be updated
     * @return the updated {@link User}
     * @throws IllegalArgumentException if the user does not have an ID, indicating it does not exist in the database
     */
    @Override
    public User updateUser(final User user) {
        log.info("Updating User {}", user);
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not existing in DB, update is not permitted!");
        }
        return userRepository.save(user);
    }
}