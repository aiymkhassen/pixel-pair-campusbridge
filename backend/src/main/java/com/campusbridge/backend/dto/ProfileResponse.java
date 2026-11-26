package com.campusbridge.backend.dto;

import com.campusbridge.backend.entity.StudentType;

public record ProfileResponse(
    Long id,
    String fullName,
    String email,
    String course,
    Integer yearOfStudy,
    StudentType studentType,
    String spokenLanguage,
    String languageToLearn,
    boolean availableAsBuddy
) {
}
