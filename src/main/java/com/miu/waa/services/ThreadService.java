package com.miu.waa.services;

import com.miu.waa.dto.request.ThreadCreateDto;
import com.miu.waa.dto.response.ThreadResponseDto;

import java.util.List;

public interface ThreadService {
    List<ThreadResponseDto> findAll();
    ThreadResponseDto createThread(ThreadCreateDto threadCreateDto);
    ThreadResponseDto updateThread(Long id, ThreadCreateDto threadCreateDto);
    ThreadResponseDto getThreadById(Long id);
    void deleteThread(Long id);
}
