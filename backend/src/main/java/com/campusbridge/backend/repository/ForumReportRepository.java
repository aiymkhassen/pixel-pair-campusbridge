package com.campusbridge.backend.repository;

import com.campusbridge.backend.entity.ForumReport;
import com.campusbridge.backend.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumReportRepository
        extends JpaRepository<ForumReport, Long> {

    List<ForumReport> findAllByOrderByCreatedAtDesc();

    List<ForumReport> findByStatusOrderByCreatedAtDesc(
        ReportStatus status);

    List<ForumReport> findByReporterId(Long reporterId);

    boolean existsByReporterIdAndPostIdAndStatus(
        Long reporterId, Long postId, ReportStatus status);

    boolean existsByReporterIdAndCommentIdAndStatus(
        Long reporterId, Long commentId, ReportStatus status);
}
