package com.miu.waa.services;

import com.miu.waa.dto.request.SurveyRequestDto;
import com.miu.waa.entities.Survey;
import com.miu.waa.entities.SurveyStatus;
import com.miu.waa.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface SurveyService {
    Survey createSurvey(SurveyRequestDto surveyRequestDto);

    List<Survey> getAllSurveys() ;

    Survey getSurveyById(Long id);

    Survey updateSurvey(Long id, String title, String description, SurveyStatus status);

    void deleteSurvey(Long id) ;

    List<Survey> findAllByCreatorId(Long creatorId);
}
