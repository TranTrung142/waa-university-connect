package com.miu.waa.services.impl;

import com.miu.waa.entities.Student;
import com.miu.waa.repositories.StudentRepository;
import com.miu.waa.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

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
}
