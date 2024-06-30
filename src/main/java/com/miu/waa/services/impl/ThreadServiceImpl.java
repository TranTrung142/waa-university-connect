package com.miu.waa.services.impl;

import com.miu.waa.dto.request.ThreadCreateDto;
import com.miu.waa.dto.response.ThreadResponseDto;
import com.miu.waa.entities.DiscussionCategory;
import com.miu.waa.mapper.ThreadDtoMapper;
import com.miu.waa.repositories.ThreadRepository;
import com.miu.waa.services.ThreadService;
import com.miu.waa.entities.Thread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {
    private ThreadRepository threadRepository;

    @Override
    public ThreadResponseDto getThreadById(Long id) {
        Optional<Thread> thread = threadRepository.findById(id);
        if (thread.isPresent()) {
            return ThreadDtoMapper.dtoMapper.threadToThreadResponseDto(thread.get());
        }
        throw new NoSuchElementException("Thread not found");
    }

    @Override
    public List<ThreadResponseDto> findAll() {
        List<Thread> threads = threadRepository.findAll();
        return threads.stream()
                .map(ThreadDtoMapper.dtoMapper::threadToThreadResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ThreadResponseDto createThread(ThreadCreateDto threadCreateDto) {
        Thread thread = ThreadDtoMapper.dtoMapper.threadCreateDtoToThread(threadCreateDto);
        thread.setCategory(ThreadDtoMapper.dtoMapper.map(threadCreateDto.getCategoryId(), DiscussionCategory.class));
        thread = threadRepository.save(thread);
        return ThreadDtoMapper.dtoMapper.threadToThreadResponseDto(thread);
    }

    @Override
    public ThreadResponseDto updateThread(Long id, ThreadCreateDto threadDTO) {
        Optional<Thread> thread = threadRepository.findById(id);
        if (thread.isPresent()) {
            Thread threadToUpdate = thread.get();
            threadToUpdate.setTitle(threadDTO.getTitle());
            threadToUpdate.setCategory(ThreadDtoMapper.dtoMapper.map(threadDTO.getCategoryId(), DiscussionCategory.class));
            threadToUpdate = threadRepository.save(threadToUpdate);
            return ThreadDtoMapper.dtoMapper.threadToThreadResponseDto(threadToUpdate);
        }
        throw new NoSuchElementException("Thread not found");
    }

    @Override
    public void deleteThread(Long id) {
        threadRepository.deleteById(id);
    }
}

