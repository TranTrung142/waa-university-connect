package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.ReportDto;
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
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportDto reportDto, HttpServletRequest request) {
        Optional<User> userLogin = RequestUtil.getUserLogin(request);
        User reportedUser = userService.getUserById(reportDto.getReportedUserId());
        if (reportedUser == null || userLogin.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "reportedUserId not found!", null));
        }
        return ResponseEntity.ok(new SuccessResponse(reportService.createReport(reportDto, userLogin.get().getId())));
    }

    @GetMapping
    public ResponseEntity<?> getReports() {
        return ResponseEntity.ok(new SuccessResponse(reportService.getReports()));
    }
}
