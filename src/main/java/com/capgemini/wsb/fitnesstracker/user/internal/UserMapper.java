package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.UserSimpleDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Converts a {@link User} entity to a {@link UserDto}.
     *
     * @param user the {@link User} entity to be converted
     * @return a {@link UserDto} representation of the user
     */
    public UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Converts a {@link User} entity to a {@link UserSimpleDto}.
     * The {@link UserSimpleDto} contains only the user's basic information: ID, first name, and last name.
     *
     * @param user the {@link User} entity to be converted
     * @return a {@link UserSimpleDto} representation of the user
     */
    UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    /**
     * Converts a {@link User} entity to a {@link UserEmailDto}.
     * The {@link UserEmailDto} contains only the user's ID and email.
     *
     * @param user the {@link User} entity to be converted
     * @return a {@link UserEmailDto} representation of the user
     */
    UserEmailDto toEmailDto(User user) {
        return new UserEmailDto(
                user.getId(),
                user.getEmail()
        );
    }

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

}
