package com.miu.waa.services;

import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    Student save(Student student);
    Student update(Student student);
    Student uploadProfileImage(Long id, MultipartFile image);
    List<UpcomingEventResponseDto> findAllUpcomingPublishedEvent();
    void joinEvent(Long eventId);
}
