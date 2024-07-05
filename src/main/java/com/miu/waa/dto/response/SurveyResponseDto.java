package com.miu.waa.dto.response;

import com.miu.waa.entities.SurveyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyResponseDto {

    private Long id;
    private String title;
    private String description;
    private Long creatorId; // Assuming only the ID is needed in response

    public SurveyResponseDto(Long id, String title, String description, Long creatorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }
}

