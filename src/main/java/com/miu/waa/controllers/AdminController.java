package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.User;
import com.miu.waa.entities.UserStatus;
import com.miu.waa.services.EventService;
import com.miu.waa.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final EventService eventService;

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
    public ResponseEntity<?> getAllEvent(@RequestParam(required = false) EventFilterDto filterDto) {
        try {
            if (filterDto == null) {
                filterDto = new EventFilterDto();
                filterDto.setStatus(null); // or set a default value
                filterDto.setDate(null);   // or set a default value
            }
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
}
