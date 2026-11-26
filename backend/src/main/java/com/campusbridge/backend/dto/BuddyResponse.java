package com.campusbridge.backend.dto;

public record BuddyResponse(
    Long id,
    String fullName,
    String course,
    Integer yearOfStudy,
    String spokenLanguage,
    String languageToLearn,
    int matchScore
) {
}
