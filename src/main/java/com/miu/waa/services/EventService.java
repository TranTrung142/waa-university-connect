package com.miu.waa.services;

import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.dto.response.EventAttandenceResponseDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.Student;

import java.util.List;

public interface EventService {
    List<EventResponseDto> findAll(EventFilterDto dto);
    EventResponseDto findById(Long id);
    EventResponseDto  save(Event event);
    EventResponseDto publishEvent(Long eventId) throws Exception;
    EventResponseDto update(Long id,EventCreateDto event) throws Exception;
    EventResponseDto updateStatus(Long eventId, EventStatus eventStatus) throws Exception;
    EventResponseDto delete(Long eventId) throws Exception;
    List<UpcomingEventResponseDto> findAllUpcomingPublishedEvent();
    void joinEvent(Long eventId);
    List<EventAttandenceResponseDto> findEventAttendance(Long eventId);
}
