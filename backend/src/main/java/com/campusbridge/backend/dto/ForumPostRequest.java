package com.campusbridge.backend.dto;

public record ForumPostRequest(
    String title,
    String content,
    boolean anonymous
) {
}