package com.miu.waa.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
public class EventCreateDto {
    private String title;

    private String description;

    private LocalDateTime eventDateTime;
}
