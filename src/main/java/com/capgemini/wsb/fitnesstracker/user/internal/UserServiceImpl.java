package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        log.info("Deleting User with id: {}", userId);

        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            userRepository.delete(user.get());
            log.info("Successfully deleted user with id: {}", userId);
        } else {
            log.info("Failed to delete user with id: {}", userId);
        }

    }

    @Override
    public Optional<User> getUserDetails(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            log.info("User with id:{} was found.", userId);
        } else {
            log.info("User with id:{} could not be found.", userId);
        }
        return user;
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(final User user) {
        log.info("Updating User {}", user);
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not existing in DB, update is not permitted!");
        }
        return userRepository.save(user);
    }
}