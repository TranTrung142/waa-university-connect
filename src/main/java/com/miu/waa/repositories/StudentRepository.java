package com.miu.waa.repositories;

import com.miu.waa.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByStudentId(Long studentId);

    @Query("SELECT s.studentId FROM Student s ORDER BY s.studentId DESC")
    Optional<Long> findTopByOrderByStudentIdDesc();

    default Long generateStudentId(){
        Optional<Long> optionalStudentId=findTopByOrderByStudentIdDesc();
        Long topStudentId = optionalStudentId.orElse(Long.valueOf(617500));
        return topStudentId+1;
    }
}
