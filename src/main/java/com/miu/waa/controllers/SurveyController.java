package com.miu.waa.controllers;

import com.miu.waa.dto.request.*;
import com.miu.waa.dto.response.SurveyResponseDto;
import com.miu.waa.entities.Survey;
import com.miu.waa.entities.SurveyStatus;
import com.miu.waa.services.SurveyService;
import com.miu.waa.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> createSurvey(@RequestBody SurveyRequestDto surveyDto) {
        try {
            Survey createdSurvey = surveyService.createSurvey(surveyDto);
            SurveyResponseDto responseDto = mapEntityToResponseDto(createdSurvey);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSurveys() {
        try {
            List<SurveyResponseDto> surveys = surveyService.getAllSurveys().stream()
                    .map(this::mapEntityToResponseDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(surveys, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSurveyById(@PathVariable("id") Long id) {
        try {
            Survey survey = surveyService.getSurveyById(id);
            if (survey == null) {
                return new ResponseEntity<>("Survey not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(mapEntityToResponseDto(survey), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyResponseDto> updateSurvey(@PathVariable("id") Long id, @RequestBody SurveyRequestDto  surveyDto) {
        Survey updatedSurvey = surveyService.updateSurvey(id, surveyDto.getTitle(), surveyDto.getDescription(), SurveyStatus.ACTIVE);
        return new ResponseEntity<>(mapEntityToResponseDto(updatedSurvey), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable("id") Long id) {
        surveyService.deleteSurvey(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Helper method to map SurveyRequestDTO to Survey entity
    private Survey mapRequestDtoToEntity(SurveyRequestDto requestDto) {
       return new Survey(requestDto.getTitle(), requestDto.getDescription(),SurveyStatus.ACTIVE,userService.getUserById(requestDto.getCreatorId()));
    }

    // Helper method to map Survey entity to SurveyResponseDTO
    private SurveyResponseDto mapEntityToResponseDto(Survey survey) {
        return new SurveyResponseDto(survey.getId(), survey.getTitle(), survey.getDescription(), survey.getCreator().getId());
    }
}
