package com.miu.waa.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SurveyRequestDto {

    private Long id;
    private String title;
    private String description;
    private Long creatorId;

    public SurveyRequestDto(Long id, String title, String description, Long creatorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }
}
