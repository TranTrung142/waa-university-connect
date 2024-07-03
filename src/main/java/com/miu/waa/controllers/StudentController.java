package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.Student;
import com.miu.waa.entities.User;
import com.miu.waa.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllUsers() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student studentDto) {
        return ResponseEntity.ok(studentService.save(studentDto));
    }

    @PostMapping("/{id}/uploadImages")
    public ResponseEntity<Student> uploadProfileImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(studentService.uploadProfileImage(id, image));
    }

    @GetMapping("/upcoming-events")
    public ResponseEntity<?> getAllUpcomingEvents() {
        List<UpcomingEventResponseDto> result=studentService.findAllUpcomingPublishedEvent();
        return ResponseEntity.ok(result);
    }
    @PostMapping("/join-event/{eventId}")
    public ResponseEntity<?> joinEvent(@PathVariable Long eventId) {
        try {
            studentService.joinEvent(eventId);
            return ResponseEntity.ok(new SuccessResponse("Joined successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

}
