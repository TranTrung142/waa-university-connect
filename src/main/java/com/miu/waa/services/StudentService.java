package com.miu.waa.services;

import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
import com.miu.waa.entities.Student;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> findAll();
    StudentResponseDto findById(Long id);
    StudentResponseDto createStudent(StudentCreateDto student);
    StudentResponseDto updateStudent(Long studentId,StudentCreateDto studetDto);
}
