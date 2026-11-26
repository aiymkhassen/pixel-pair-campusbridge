package com.campusbridge.backend.dto;

import java.time.LocalDateTime;

public record ForumCommentResponse(
    Long id,
    Long postId,
    Long parentCommentId,
    Long authorId,
    String authorName,
    String content,
    boolean anonymous,
    boolean ownComment,
    LocalDateTime createdAt
) {
}
