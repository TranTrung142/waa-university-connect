package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
import com.miu.waa.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final StudentService studentService;
    @PostMapping
    public ResponseEntity<?> register(@RequestBody StudentCreateDto studentDto) {
        try {
            StudentResponseDto studentResponseDto = studentService.createStudent(studentDto);
            return ResponseEntity.ok(new SuccessResponse(studentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> update(@PathVariable Long studentId,@RequestBody StudentCreateDto studentDto) {
        try {
            StudentResponseDto studentResponseDto = studentService.updateStudent(studentId,studentDto);
            return ResponseEntity.ok(new SuccessResponse(studentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getByStudentId(@PathVariable Long studentId) {
        try {
            StudentResponseDto studentResponseDto = studentService.findByStudentId(studentId);
            return ResponseEntity.ok(new SuccessResponse(studentResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
}
