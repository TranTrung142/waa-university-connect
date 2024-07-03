package com.miu.waa.services.impl;

import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.User;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.repositories.EventRepository;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.services.EventService;
import com.miu.waa.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final Long currentUserId;

    public EventServiceImpl(EventRepository eventRepository,
                            UserRepository userRepository) {
        currentUserId=Long.valueOf(1);
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

//    private Optional<Event> getEventById(Long eventId) {
//        Optional<User> userLogin = RequestUtil.getUserLogin(request);
//    }

    @Override
    public List<EventResponseDto> findAll() {
        return eventRepository.findAll().stream()
                .map(EventDtoMapper.dtoMapper::eventToEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDto findById(Long id) {
        try{
            Event event=eventRepository.findById(id)
                    .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

            return EventDtoMapper.dtoMapper.eventToEventResponseDto(event);
        }
        catch(NoSuchElementException e){
            throw e;
        }
    }

    @Override
    public EventResponseDto save(Event event) {
        try{
            event.setStatus(EventStatus.DRAFT);
            event.setCreatedOn(LocalDateTime.now());
            event = eventRepository.save(event);
            return EventDtoMapper.dtoMapper.eventToEventResponseDto(event);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public EventResponseDto update(Long id,EventCreateDto eventUpdateDto) throws Exception {
        try{
            if(eventUpdateDto.getEventDateTime().isBefore( LocalDateTime.now())){
                throw new NoSuchElementException("Event date can not be less than current date");
            }

            Event event=eventRepository.findById(id)
                    .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

            if(event.getStatus()!=EventStatus.DRAFT){
                throw new Exception("Event can not be updated!!!");
            }

            event.setEventDateTime(eventUpdateDto.getEventDateTime());
            event.setTitle(eventUpdateDto.getTitle());
            event.setDescription(eventUpdateDto.getDescription());
            event=eventRepository.save(event);
            return EventDtoMapper.dtoMapper.eventToEventResponseDto(event);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public EventResponseDto updateStatus(Long eventId,EventStatus eventStatus) throws Exception {
        try{
            Event event=eventRepository.findById(eventId)
                    .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

            if(eventStatus==EventStatus.DRAFT ||
                (eventStatus==EventStatus.PUBLISHED && event.getStatus()!=EventStatus.DRAFT) ||
                (eventStatus==EventStatus.CLOSED && event.getStatus()!=EventStatus.STARTED) ||
                (eventStatus!=EventStatus.PUBLISHED && event.getStatus()!=EventStatus.PUBLISHED)
            ){
                throw new Exception("Event status can not be updated!!!");
            }
            event.setStatus(eventStatus);
            if(eventStatus==EventStatus.PUBLISHED){
                event.setPublishedOn(LocalDateTime.now());
            }

            event=eventRepository.save(event);
            return EventDtoMapper.dtoMapper.eventToEventResponseDto(event);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public EventResponseDto delete(Long eventId) throws Exception {
        Event event=eventRepository.findById(eventId)
                .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

        if(event.getStatus()!=EventStatus.DRAFT){
            throw new Exception("Event can not be deleted");
        }
        eventRepository.delete(event);
        return EventDtoMapper.dtoMapper.eventToEventResponseDto(event);
    }
}
