package com.miu.waa.services.impl;

import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.*;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.repositories.EventAttendanceRepository;
import com.miu.waa.repositories.EventRepository;
import com.miu.waa.repositories.StudentRepository;
import com.miu.waa.services.FileStorageService;
import com.miu.waa.services.StudentService;
import com.miu.waa.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;
    private final FileStorageService fileStorageService;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("Student not found!!"));
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        Student obj=findById(student.getId());
        obj.setFirstName(student.getFirstName());
        obj.setLastName(student.getLastName());
        obj.setMajor(student.getMajor());
        return studentRepository.save(obj);
    }

    public Student uploadProfileImage(Long id, MultipartFile image) {
        Student student = findById(id);
        String savedImages = fileStorageService.storeFile(image);
        student.setProfilePictureURL(savedImages);
        studentRepository.save(student);
        return student;
    }
    @Override
    public List<EventResponseDto> findAllStudentEvents(Long studentId,EventFilterDto dto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found!!"));

        return eventRepository.findAllStudentEvents(student.getId(),dto).stream()
                .map(EventDtoMapper.dtoMapper::eventToEventResponseDto)
                .collect(Collectors.toList());
    }
}
