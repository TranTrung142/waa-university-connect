package com.miu.waa.services.impl;


import com.miu.waa.dto.request.SurveyRequestDto;
import com.miu.waa.entities.*;
import com.miu.waa.repositories.*;
import com.miu.waa.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    public Survey createSurvey(SurveyRequestDto surveyRequestDto) {
        User creator = userRepository.findById(surveyRequestDto.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + surveyRequestDto.getCreatorId()));
        Survey survey = new Survey(surveyRequestDto.getTitle(), surveyRequestDto.getDescription(), SurveyStatus.ACTIVE, creator);
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurveyById(Long id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));
    }

    public Survey updateSurvey(Long id, String title, String description, SurveyStatus status) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey not found with id: " + id));

        survey.setTitle(title);
        survey.setDescription(description);
        survey.setStatus(status);
        survey.setUpdatedAt(LocalDateTime.now());

        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    public List<Survey> findAllByCreatorId(Long creatorId) {
        return surveyRepository.findAllByCreatorId(creatorId);
    }
}

