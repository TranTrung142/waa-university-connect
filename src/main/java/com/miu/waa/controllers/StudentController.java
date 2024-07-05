package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
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
    public ResponseEntity<List<StudentResponseDto>> getAllUsers() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getUserById(@PathVariable Long id) {
        try{
            StudentResponseDto studentResponseDto=studentService.findById(id);
            return ResponseEntity.ok(studentResponseDto);
        }
        catch (Exception e){
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentCreateDto studentDto) {
        try {
            StudentResponseDto studentResponseDto = studentService.createStudent(studentDto);
            return ResponseEntity.ok(new SuccessResponse(studentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId,@RequestBody StudentCreateDto studentDto) {
        try {
            StudentResponseDto studentResponseDto = studentService.updateStudent(studentId,studentDto);
            return ResponseEntity.ok(new SuccessResponse(studentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", null));
        }
    }

    @PostMapping("/{id}/uploadImages")
    public ResponseEntity<Student> uploadProfileImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(studentService.uploadProfileImage(id, image));
    }

    @GetMapping("/{studentId}/events")
    public ResponseEntity<?> getStudentEvent(@PathVariable Long studentId,@RequestParam(required = false) EventFilterDto filterDto) {
        try {
            if (filterDto == null) {
                filterDto = new EventFilterDto();
                filterDto.setStatus(null);
                filterDto.setDate(null);
            }
            return ResponseEntity.ok(new SuccessResponse(studentService.findAllStudentEvents(studentId,filterDto)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
}
