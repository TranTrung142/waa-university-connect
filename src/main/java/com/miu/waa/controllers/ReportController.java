package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.UserDto;
import com.miu.waa.dto.request.ReportDto;
import com.miu.waa.entities.Report;
import com.miu.waa.entities.User;
import com.miu.waa.services.ReportService;
import com.miu.waa.services.UserService;
import com.miu.waa.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportDto reportDto, HttpServletRequest request) {
        try {
            Optional<User> userLogin = RequestUtil.getUserLogin(request);
            UserDto reportedUser = userService.getUserById(reportDto.getReportedUserId());
            if (reportedUser == null || userLogin.isEmpty()) {
                return ResponseEntity.status(404).body(new ErrorResponse(404, "reportedUserId not found!", null));
            }
            return ResponseEntity.ok(new SuccessResponse(reportService.createReport(reportDto, userLogin.get().getId())));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        try {
            Optional<Report> report = reportService.getReportById(id);
            if (report.isEmpty()) {
                return ResponseEntity.status(404).body(new ErrorResponse(404, "Report not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(report));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getReports(HttpServletRequest request) {
        try {
            Optional<User> userLogin = RequestUtil.getUserLogin(request);
            if (userLogin.isEmpty()) {
                return ResponseEntity.status(404).body(new ErrorResponse(404, "User not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(reportService.getReportsByUserId(userLogin.get().getId())));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }
}
