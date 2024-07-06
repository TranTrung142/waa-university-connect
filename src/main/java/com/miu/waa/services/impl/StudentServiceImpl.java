package com.miu.waa.services.impl;

import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.*;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.repositories.EventAttendanceRepository;
import com.miu.waa.repositories.EventRepository;
import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
import com.miu.waa.entities.Student;
import com.miu.waa.mapper.StudentDtoMapper;
import com.miu.waa.repositories.StudentRepository;
import com.miu.waa.services.FileStorageService;
import com.miu.waa.services.StudentService;
import com.miu.waa.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;
    private final FileStorageService fileStorageService;
    @Value("${spring.security.default.password}")
    private String defaultPassword;
  
    @Override
    public List<StudentResponseDto> findAll() {
        try{
            List<Student> students=studentRepository.findAll();
            return students.stream()
                    .map(StudentDtoMapper.dtoMapper::studentToStudentResponseDto)
                    .collect(Collectors.toList());
        }
        catch(NoSuchElementException e){
            throw e;
        }
    }

    @Override
    public StudentResponseDto findById(Long id) {
        try{
            Optional<Student> optionalStudent=studentRepository.findByStudentId(id);
            if(optionalStudent.isEmpty()){
                throw new NoSuchElementException("Student not found!!");
            }
            return StudentDtoMapper.dtoMapper.
                    studentToStudentResponseDto(optionalStudent.get());
        }
        catch(NoSuchElementException e){
            throw e;
        }
    }

    @Override
    public StudentResponseDto findByStudentId(Long studentId) {
        Student student=studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        return StudentDtoMapper.dtoMapper.studentToStudentResponseDto(student);
    }

    @Override
    public StudentResponseDto createStudent(StudentCreateDto dto) {
        try{
            Student student=StudentDtoMapper.dtoMapper.studentCreateDtoToStudent(dto);
            student.setStudentId(studentRepository.generateStudentId());
            student.setCreatedAt(LocalDateTime.now());
            student.setStatus(UserStatus.PENDING);
            student.setRole(UserRole.STUDENT);
            student.setPassword(defaultPassword);
            student = studentRepository.save(student);
            return StudentDtoMapper.dtoMapper.studentToStudentResponseDto(student);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public StudentResponseDto updateStudent(Long studentId,StudentCreateDto studentDto) {
        try{
            Student student=studentRepository.findByStudentId(studentId)
                    .orElseThrow(() -> new NoSuchElementException("Student not found"));
//            if (!user.getRole().equals(UserRole.ADMIN) && !student.getId().equals(user.getId())) {
//                throw new NoSuchElementException("You do not have permission to update this student.");
//            }
            if(!student.getStatus().equals(UserStatus.PENDING)){
                throw new NoSuchElementException("You cannot modify data from here,contact admin or login student portal");
            }

            student.setFirstName(studentDto.getFirstName());
            student.setLastName(studentDto.getLastName());
            student.setEmail(studentDto.getEmail());
            student.setPhone(studentDto.getPhone());
            student.setProfilePictureURL(studentDto.getProfilePictureURL());
            student.setMajor(studentDto.getMajor());
            student.setAcademicAchievements(studentDto.getAcademicAchievements());
            student.setInterests(studentDto.getInterests());
            student.setExtracurricularActivities(studentDto.getExtracurricularActivities());
            student = studentRepository.save(student);
            return StudentDtoMapper.dtoMapper.studentToStudentResponseDto(student);
        }
        catch (Exception e){
            throw e;
        }
    }

    public Student uploadProfileImage(Long id, MultipartFile image) {
        Student student = studentRepository.findById(id).get();
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
