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

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/simple")
    public List<UserSimpleDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/{id}")
    public Optional<UserDto> getUserDetails(@PathVariable long id){
        return userService.getUserDetails(id).stream().map(userMapper::toDto).findAny();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User tempUser = new User(userDto.firstName(), userDto.lastName(), userDto.birthdate(), userDto.email());
        return userService.createUser(tempUser);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) throws InterruptedException {
        userService.deleteUser(userId);
    }

    @GetMapping("/email")
    public List<UserEmailDto> getUserByEmail(@RequestParam String email) {
        email = email.toLowerCase();
        return userService.getUserByEmail(email).stream().map(userMapper::toEmailDto).toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUserOlderThan(@PathVariable long time){
        List<User> tempListOfUsers = userService.findAllUsers();
        Iterator<User> iterator = tempListOfUsers.iterator();

        System.out.println("Im getting users older than " + String.valueOf(time));
        long years = 0;
        while (iterator.hasNext()){
            years = ChronoUnit.YEARS.between(iterator.next().getBirthdate(), LocalDate.now());
            //System.out.println("Birthdate" + iterator.next().getBirthdate().toString() + "Now" + LocalDate.now().toString());
            if(years <= time){
                iterator.remove();
            }
        }
        return tempListOfUsers.stream().map(userMapper::toDto).toList();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody UserDto userDto) throws InterruptedException {
        User tempUser = new User(userId, userDto.firstName(), userDto.lastName(), userDto.birthdate(), userDto.email());
        return userService.updateUser(tempUser);
    }
}