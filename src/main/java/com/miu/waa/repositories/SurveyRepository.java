package com.miu.waa.repositories;

import com.miu.waa.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    // Additional query methods can be defined here if needed
    List<Survey> findAllByCreatorId(Long creatorId);
}
