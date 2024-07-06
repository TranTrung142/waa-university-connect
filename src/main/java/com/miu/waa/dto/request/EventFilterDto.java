package com.miu.waa.dto.request;

import com.miu.waa.entities.EventStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class EventFilterDto {
    private EventStatus status;
    private LocalDate date;
}
