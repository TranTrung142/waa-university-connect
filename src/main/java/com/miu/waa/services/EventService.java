package com.miu.waa.services;

import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.Student;

import java.util.List;

public interface EventService {
    List<EventResponseDto> findAll();
    EventResponseDto findById(Long id);
    EventResponseDto save(EventCreateDto event);
    EventResponseDto update(Long id,EventCreateDto event) throws Exception;
    EventResponseDto updateStatus(Long eventId, EventStatus eventStatus) throws Exception;
//    EventResponseDto publish(Long eventId);
    EventResponseDto delete(Long eventId) throws Exception;
}
