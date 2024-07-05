package com.miu.waa.services;

import com.miu.waa.dto.request.StudentCreateDto;
import com.miu.waa.dto.response.StudentResponseDto;
import com.miu.waa.entities.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
// <<<<<<< feature/student-profile-creation
    List<StudentResponseDto> findAll();
    StudentResponseDto findById(Long id);
    StudentResponseDto createStudent(StudentCreateDto student);
    StudentResponseDto updateStudent(Long studentId,StudentCreateDto studetDto);
// =======
//     List<Student> findAll();
//     Student findById(Long id);
//     Student save(Student student);
//     Student update(Student student);
//     Student uploadProfileImage(Long id, MultipartFile image);
// >>>>>>> dev
}
