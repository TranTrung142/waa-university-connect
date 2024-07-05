package com.miu.waa.dto.response;

import com.miu.waa.entities.Event;
import com.miu.waa.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class EventAttandenceResponseDto {
    private EventAttendedUserDto user;
    private LocalDateTime checkInTime;
}
