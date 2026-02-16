package com.catodev.onechallengeforum.dto.auth;

public record RegisterUserDto(
        String name,
        String email,
        String password
) {}
