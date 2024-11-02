package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;


/**
 * DTO representing a user's email information.
 *
 * @param id    the unique identifier of the user
 * @param email the email address of the user
 */
public record UserEmailDto(@Nullable long id, String email) {
}
