package com.miu.waa.services;

import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;

public interface RegisterService {
    StudentResponseDto register(StudentCreateDto student);
    StudentResponseDto update(Long studentId,StudentCreateDto studetDto);
    StudentResponseDto findByStudentId(Long id);
}
