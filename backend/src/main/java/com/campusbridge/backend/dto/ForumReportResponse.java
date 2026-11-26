package com.campusbridge.backend.dto;

import com.campusbridge.backend.entity.ReportStatus;
import java.time.LocalDateTime;

public record ForumReportResponse(
    Long id,
    Long reporterId,
    String reporterName,
    Long postId,
    Long commentId,
    String reportedContent,
    String reason,
    ReportStatus status,
    LocalDateTime createdAt
) {
}
