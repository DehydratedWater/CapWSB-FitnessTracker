package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

public record UserEmailDto(@Nullable long id, String email) {
}
