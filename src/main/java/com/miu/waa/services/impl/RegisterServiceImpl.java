package com.miu.waa.services.impl;

import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
import com.miu.waa.services.RegisterService;

public class RegisterServiceImpl implements RegisterService {
    @Override
    public StudentResponseDto register(StudentCreateDto student) {
        return null;
    }

    @Override
    public StudentResponseDto update(Long studentId, StudentCreateDto studetDto) {
        return null;
    }

    @Override
    public StudentResponseDto findByStudentId(Long id) {
        return null;
    }
}
