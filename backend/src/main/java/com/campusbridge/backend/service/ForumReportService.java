package com.campusbridge.backend.service;

import com.campusbridge.backend.dto.ForumReportRequest;
import com.campusbridge.backend.dto.ForumReportResponse;
import com.campusbridge.backend.entity.*;
import com.campusbridge.backend.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumReportService {

    private final ForumReportRepository reportRepository;
    private final ForumPostRepository postRepository;
    private final ForumCommentRepository commentRepository;
    private final UserRepository userRepository;

    public ForumReportService(
            ForumReportRepository reportRepository,
            ForumPostRepository postRepository,
            ForumCommentRepository commentRepository,
            UserRepository userRepository) {

        this.reportRepository = reportRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // Submit a report
    @Transactional
    public void createReport(
            String email, ForumReportRequest request) {

        User reporter = findUser(email);

        if (reporter.getCourse() == null ||
            reporter.getCourse().isBlank() ||
            reporter.getYearOfStudy() == null ||
            reporter.getStudentType() == null) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Complete your community profile first");
        }

        if ((request.postId() == null) ==
            (request.commentId() == null)) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Report either a post or a comment");
        }

        if (request.reason() == null ||
            request.reason().isBlank() ||
            request.reason().trim().length() > 1000) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Provide a valid report reason");
        }

        if (request.postId() != null) {

            if (!postRepository.existsById(request.postId())) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Post not found");
            }

            if (reportRepository
                    .existsByReporterIdAndPostIdAndStatus(
                        reporter.getId(),
                        request.postId(),
                        ReportStatus.PENDING)) {

                throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You already reported this post");
            }
        }

        if (request.commentId() != null) {

            if (!commentRepository.existsById(
                    request.commentId())) {

                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Comment not found");
            }

            if (reportRepository
                    .existsByReporterIdAndCommentIdAndStatus(
                        reporter.getId(),
                        request.commentId(),
                        ReportStatus.PENDING)) {

                throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You already reported this comment");
            }
        }

        ForumReport report = new ForumReport();

        report.setReporter(reporter);
        report.setPostId(request.postId());
        report.setCommentId(request.commentId());
        report.setReason(request.reason().trim());
        report.setStatus(ReportStatus.PENDING);

        reportRepository.save(report);
    }

    // Administrator views reports
    @Transactional(readOnly = true)
    public List<ForumReportResponse> getReports(String email) {

        requireAdmin(email);

        return reportRepository
            .findAllByOrderByCreatedAtDesc()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    // Administrator removes inappropriate content
    @Transactional
    public ForumReportResponse resolveReport(
            String email, Long reportId) {

        requireAdmin(email);

        ForumReport report = findPendingReport(reportId);

        if (report.getPostId() != null) {

            postRepository.findById(report.getPostId())
                .ifPresent(postRepository::delete);
        }

        if (report.getCommentId() != null) {

            commentRepository.findById(report.getCommentId())
                .ifPresent(this::deleteCommentTree);
        }

        commentRepository.flush();
        postRepository.flush();

        report.setStatus(ReportStatus.RESOLVED);

        return toResponse(reportRepository.save(report));
    }

    // Administrator dismisses a report
    @Transactional
    public ForumReportResponse dismissReport(
            String email, Long reportId) {

        requireAdmin(email);

        ForumReport report = findPendingReport(reportId);

        report.setStatus(ReportStatus.DISMISSED);

        return toResponse(reportRepository.save(report));
    }

    private void deleteCommentTree(ForumComment comment) {

        List<ForumComment> replies =
            commentRepository.findByParentCommentId(
                comment.getId());

        for (ForumComment reply : replies) {
            deleteCommentTree(reply);
        }

        commentRepository.delete(comment);
        commentRepository.flush();
    }

    private ForumReport findPendingReport(Long reportId) {

        ForumReport report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Report not found"));

        if (report.getStatus() != ReportStatus.PENDING) {

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Report has already been reviewed");
        }

        return report;
    }

    private User findUser(String email) {

        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"));
    }

    private void requireAdmin(String email) {

        User user = findUser(email);

        if (user.getRole() != Role.ADMIN) {

            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Administrator access required");
        }
    }

    private ForumReportResponse toResponse(
            ForumReport report) {

        String content = "Content no longer available";

        if (report.getPostId() != null) {

            content = postRepository
                .findById(report.getPostId())
                .map(post -> post.getTitle() + ": " +
                    post.getContent())
                .orElse(content);
        }

        if (report.getCommentId() != null) {

            content = commentRepository
                .findById(report.getCommentId())
                .map(ForumComment::getContent)
                .orElse(content);
        }

        return new ForumReportResponse(
            report.getId(),
            report.getReporter().getId(),
            report.getReporter().getFullName(),
            report.getPostId(),
            report.getCommentId(),
            content,
            report.getReason(),
            report.getStatus(),
            report.getCreatedAt()
        );
    }
}
