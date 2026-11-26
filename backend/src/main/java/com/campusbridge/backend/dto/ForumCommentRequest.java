package com.campusbridge.backend.dto;

public record ForumCommentRequest(
    String content,
    Long parentCommentId,
    boolean anonymous
) {
}
