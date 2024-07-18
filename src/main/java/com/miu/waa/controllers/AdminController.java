package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.entities.ReportStatus;
import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.User;
import com.miu.waa.entities.UserStatus;
import com.miu.waa.services.ReportService;
import com.miu.waa.services.EventService;
import com.miu.waa.services.StudentService;
import com.miu.waa.services.UserService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final EventService eventService;
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

    @PutMapping("/student/approve/{id}")
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

    @PutMapping("/student/deactivate/{id}")
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
    @GetMapping("/events")
//    public ResponseEntity<?> getAllEvent(@RequestParam(required = false) EventFilterDto filterDto) {
    public ResponseEntity<?> getAllEvent(@RequestParam(required = false) String status,
                                         @RequestParam(required = false) String date) {
        try {
            EventFilterDto filterDto = new EventFilterDto();
            if (status != null) {
                try {
                    filterDto.setStatus(EventStatus.valueOf(status));
                } catch (IllegalArgumentException e) {
                    // Handle the case where the status string does not match any enum value
                    filterDto.setStatus(null); // or set a default value
                }
            }
            if (date != null) {
                try {
                    filterDto.setDate(LocalDate.parse(date));
                } catch (DateTimeParseException e) {
                    // Handle the case where the date string is not a valid date format
                    filterDto.setDate(null); // or set a default value
                }
            }
//            if (filterDto == null) {
//                filterDto = new EventFilterDto();
//                filterDto.setStatus(null); // or set a default value
//                filterDto.setDate(null);   // or set a default value
//            }
            return ResponseEntity.ok(new SuccessResponse(eventService.findAll(filterDto)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
    @PutMapping("/events/{id}/approve")
    public ResponseEntity<?> approveEvent(@PathVariable Long id) {
        try {
            eventService.publishEvent(id);
            return ResponseEntity.ok(new SuccessResponse("Event approved successfully!!!"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
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
