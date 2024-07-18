package com.miu.waa.dto.response;

import com.miu.waa.dto.EventDto;
import com.miu.waa.dto.UserDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LiveMessageResponseDto {
    private EventDto event;

    private UserDto user;

    private String message;
}
