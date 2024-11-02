package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.List;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        String lowerEmail = email.toLowerCase();
        return findAll().stream()
                        .filter(user -> user.getEmail().toLowerCase().contains(lowerEmail))
                        .findFirst();
    }

    /**
     * Finds all users who were born before a specified date.
     *
     * @param time the date to compare each user's birthdate against
     * @return a {@link List} of {@link User} entities who are older than the specified date
     */
    default List<User> findUsersOlderThenDate(LocalDate time) {
        return findAll().stream()
                .filter(user -> time.isAfter(user.getBirthdate()))
                .toList();
    }

}
