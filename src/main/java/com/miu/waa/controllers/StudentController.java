package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.EventFilterDto;
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

    @GetMapping("/{userId}/events")
    public ResponseEntity<?> getStudentEvent(@PathVariable Long userId,@RequestParam(required = false) EventFilterDto filterDto) {
        try {
            if (filterDto == null) {
                filterDto = new EventFilterDto();
                filterDto.setStatus(null); // or set a default value
                filterDto.setDate(null);   // or set a default value
            }
            return ResponseEntity.ok(new SuccessResponse(studentService.findAllStudentEvents(userId,filterDto)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
}
