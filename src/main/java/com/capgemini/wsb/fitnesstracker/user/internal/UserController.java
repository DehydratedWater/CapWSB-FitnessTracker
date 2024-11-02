package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Retrieves a list of all users.
     *
     * @return a list of {@link UserDto} representing all users
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a list of all users with basic information.
     *
     * @return a list of {@link UserSimpleDto} representing all users with basic details
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Retrieves detailed information about a specific user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return an {@link Optional} containing {@link UserDto} if the user is found, otherwise {@link Optional#empty()}
     */
    @GetMapping("/{id}")
    public Optional<UserDto> getUserDetails(@PathVariable long id) {
        return userService.getUserDetails(id)
                .stream()
                .map(userMapper::toDto)
                .findAny();
    }

    /**
     * Adds a new user.
     *
     * @param userDto the {@link UserDto} containing information of the user to create
     * @return the created {@link User}
     * @throws InterruptedException if an interruption occurs during processing
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        System.out.println("User with e-mail: " + userDto.email() + " passed to the request");
        User tempUser = new User(userDto.firstName(), userDto.lastName(), userDto.birthdate(), userDto.email());
        return userService.createUser(tempUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     * @throws InterruptedException if an interruption occurs during processing
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) throws InterruptedException {
        userService.deleteUser(userId);
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user to search for
     * @return a list of {@link UserEmailDto} containing user email information
     */
    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .stream()
                .map(userMapper::toEmailDto)
                .toList();
    }

    /**
     * Retrieves a list of users older than the specified date.
     *
     * @param time the date in 'yyyy-MM-dd' format to filter users by age
     * @return a list of {@link UserDto} representing users older than the specified date
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getUserOlderThan(@PathVariable String time) {
        LocalDate parsedTime = LocalDate.parse(time);
        return userService.findUsersOlderThenDate(parsedTime)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Updates an existing user.
     *
     * @param userId  the ID of the user to update
     * @param userDto the {@link UserDto} containing updated user information
     * @return the updated {@link User}
     * @throws InterruptedException if an interruption occurs during processing
     */
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody UserDto userDto) throws InterruptedException {
        User tempUser = new User(userId, userDto.firstName(), userDto.lastName(), userDto.birthdate(), userDto.email());
        return userService.updateUser(tempUser);
    }
}