package com.campusbridge.backend.dto;

import java.time.LocalDateTime;

public record ForumPostResponse(
    Long id,
    Long authorId,
    String authorName,
    String course,
    Integer yearOfStudy,
    String title,
    String content,
    boolean anonymous,
    boolean ownPost,
    LocalDateTime createdAt
) {
}
