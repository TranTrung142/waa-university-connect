package com.miu.waa.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class UpcomingEventResponseDto {
    private Long id;

    private String title;

    private String description;

    private LocalDateTime eventDateTime;
}
