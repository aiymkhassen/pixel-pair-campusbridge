package com.campusbridge.backend.dto;

public record RegisterResponse(
    Long id,
    String fullName,
    String email,
    String role
) {}
