package com.miu.waa.repositories;

import com.miu.waa.entities.Student;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByStudentId(Long studentId);

    @Query("SELECT s.studentId FROM Student s ORDER BY s.studentId DESC")
    List<Long> findTopByOrderByStudentIdDesc(Pageable pageable);

    default Long generateStudentId(){
        Pageable pageable = PageRequest.of(0, 1);
        List<Long> lst=findTopByOrderByStudentIdDesc(pageable);

        Long topStudentId =Long.valueOf(617500);
        if(lst.size()>0){
            topStudentId=lst.get(0);
        }
        return topStudentId+1;
    }
}
