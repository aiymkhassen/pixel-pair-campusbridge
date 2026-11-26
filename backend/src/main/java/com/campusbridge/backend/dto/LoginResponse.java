package com.campusbridge.backend.dto;

public record LoginResponse (
    Long id,
    String fullName,
    String email,
    String role,
    String message
){
    
}
