package com.campusbridge.backend.dto;

public record ForumReportRequest(
    Long postId,
    Long commentId,
    String reason
) {
}
