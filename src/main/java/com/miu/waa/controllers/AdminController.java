package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.entities.ReportStatus;
import com.miu.waa.entities.User;
import com.miu.waa.entities.UserStatus;
import com.miu.waa.services.ReportService;
import com.miu.waa.services.UserService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final ReportService reportService;

    @GetMapping("/students")
    public ResponseEntity<?> getAllStudents() {
        try {
            return ResponseEntity.ok(new SuccessResponse(userService.getAll()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @GetMapping("/student/approve/{id}")
    public ResponseEntity<?> approveStudent(@PathVariable Long id) {
        try {
            User user = userService.updateStatusUser(id, UserStatus.ACTIVATED).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "User not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(user));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @GetMapping("/student/deactivate/{id}")
    public ResponseEntity<?> deactivateStudent(@PathVariable Long id) {
        try {
            User user = userService.updateStatusUser(id, UserStatus.DEACTIVATE).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "User not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(user));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }



    @GetMapping("/reports")
    public ResponseEntity<?> getReports(@RequestParam @Nullable ReportStatus status) {
        try {
            return ResponseEntity.ok(new SuccessResponse(reportService.searchReports(status)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @PostMapping("/reports/{reportId}/approve")
    public ResponseEntity<?> approveReport(@PathVariable Long reportId) {
        try {
            return ResponseEntity.ok(new SuccessResponse(reportService.approveReport(reportId)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse(500, "Internal server error", null));
        }
    }
}
