package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.ThreadCreateDto;
import com.miu.waa.dto.response.ThreadResponseDto;
import com.miu.waa.services.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/threads")
@RequiredArgsConstructor
public class ThreadController {
    private final ThreadService threadService;

    @GetMapping
    public ResponseEntity<?> getAllThreads() {
        try {
            return ResponseEntity.ok(new SuccessResponse(threadService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getThreadById(@PathVariable Long id) {
        try {
            ThreadResponseDto thread = threadService.getThreadById(id);
            if (thread == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "Thread not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(thread));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createThread(@RequestBody ThreadCreateDto threadDto) {
        try {
            ThreadResponseDto thread = threadService.createThread(threadDto);
            if (thread == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "Thread not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(thread));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateThread(@PathVariable Long id, @RequestBody ThreadCreateDto threadDto) {
        try {
            ThreadResponseDto thread = threadService.updateThread(id, threadDto);
            if (thread == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "Thread not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(thread));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteThread(@PathVariable Long id) {
        try {
            threadService.deleteThread(id);
            return ResponseEntity.ok(new SuccessResponse("Thread deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }
}
