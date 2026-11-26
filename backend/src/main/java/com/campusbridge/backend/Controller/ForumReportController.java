package com.campusbridge.backend.Controller;

import com.campusbridge.backend.dto.ForumReportRequest;
import com.campusbridge.backend.dto.ForumReportResponse;
import com.campusbridge.backend.service.ForumReportService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/forum/reports")
public class ForumReportController {

    private final ForumReportService reportService;

    public ForumReportController(
            ForumReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createReport(
            Principal principal,
            @RequestBody ForumReportRequest request) {

        reportService.createReport(
            principal.getName(), request);
    }

    @GetMapping
    public List<ForumReportResponse> getReports(
            Principal principal) {

        return reportService.getReports(
            principal.getName());
    }

    @PutMapping("/{reportId}/resolve")
    public ForumReportResponse resolveReport(
            Principal principal,
            @PathVariable("reportId") Long reportId) {

        return reportService.resolveReport(
            principal.getName(), reportId);
    }

    @PutMapping("/{reportId}/dismiss")
    public ForumReportResponse dismissReport(
            Principal principal,
            @PathVariable("reportId") Long reportId) {

        return reportService.dismissReport(
            principal.getName(), reportId);
    }
}
