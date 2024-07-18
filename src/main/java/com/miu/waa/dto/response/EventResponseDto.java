package com.miu.waa.dto.response;

import com.miu.waa.dto.UserDto;
import com.miu.waa.entities.EventStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class EventResponseDto {
    private Long id;
    private String title;

    private String description;

    private LocalDateTime eventDateTime;
    private EventStatus status;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private UserDto createdBy;
    private UserDto approvedBy;
}
