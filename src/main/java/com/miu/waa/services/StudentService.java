package com.miu.waa.services;

import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
import com.miu.waa.entities.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> findAll();
    StudentResponseDto findById(Long id);
    StudentResponseDto findByStudentId(Long studentId);
    StudentResponseDto createStudent(StudentCreateDto student);
    StudentResponseDto updateStudent(Long studentId,StudentCreateDto studetDto);
    Student uploadProfileImage(Long id, MultipartFile image);
    List<EventResponseDto> findAllStudentEvents(Long studentId,EventFilterDto dto);
}
