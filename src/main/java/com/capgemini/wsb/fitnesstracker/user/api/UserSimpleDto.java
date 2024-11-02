package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * DTO representing basic user information.
 *
 * @param id        the unique identifier of the user
 * @param firstName the first name of the user
 * @param lastName  the last name of the user
 */
public record UserSimpleDto(@Nullable Long id, String firstName, String lastName) {

}
