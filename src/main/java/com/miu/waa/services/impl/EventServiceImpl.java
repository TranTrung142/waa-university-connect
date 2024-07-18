package com.miu.waa.services.impl;

import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.dto.response.EventAttandenceResponseDto;
import com.miu.waa.dto.response.EventAttendedUserDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.*;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.repositories.EventAttendanceRepository;
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
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventAttendanceRepository eventAttendanceRepository;
    private final UserRepository userRepository;

    @Override
    public List<EventResponseDto> findAll(EventFilterDto dto) {
        return eventRepository.findAllEvents(dto).stream()
                .map(EventDtoMapper.dtoMapper::eventToEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDto findById(Long id) {
        try{
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Event not found with ID: " + id));
            User user = RequestUtil.getUserLogin(null)
                    .orElseThrow(() -> new NoSuchElementException("User not logged in. Please log in to continue."));
            if (user.getRole().equals(UserRole.ADMIN) || event.getCreatedBy().getId().equals(user.getId())) {
                return EventDtoMapper.dtoMapper.eventToEventResponseDto(event);
            }
            throw new NoSuchElementException("You do not have permission to access this event.");
        }
        catch(NoSuchElementException e){
            throw e;
        }
    }

    @Override
    public EventResponseDto save(Event event) {
        try{
            User user = RequestUtil.getUserLogin(null)
                    .orElseThrow(() -> new NoSuchElementException("User not logged in. Please log in to continue."));

            event.setStatus(EventStatus.DRAFT);
            event.setCreatedBy(user);
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

            User user = RequestUtil.getUserLogin(null)
                    .orElseThrow(() -> new NoSuchElementException("User not logged in. Please log in to continue."));

            if (!user.getRole().equals(UserRole.ADMIN) && !event.getCreatedBy().getId().equals(user.getId())) {
                throw new NoSuchElementException("You do not have permission to access this event.");
            }
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
    public EventResponseDto publishEvent(Long eventId) throws Exception {
        try{
            Event event=eventRepository.findById(eventId)
                    .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

            if(event.getStatus()!=EventStatus.DRAFT){
                throw new Exception("Event status can not be updated!!!");
            }
            User currentUser= RequestUtil.getUserLogin(null)
                    .orElseThrow(()->new NoSuchElementException("User not found!!!"));

            event.setStatus(EventStatus.PUBLISHED);
            event.setApprovedOn(LocalDateTime.now());
            event.setApprovedBy(currentUser);

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

            User user = RequestUtil.getUserLogin(null)
                    .orElseThrow(() -> new NoSuchElementException("User not logged in. Please log in to continue."));

            if (!user.getRole().equals(UserRole.ADMIN) && !event.getCreatedBy().getId().equals(user.getId())) {
                throw new NoSuchElementException("You do not have permission to access this event.");
            }

            if(eventStatus==EventStatus.DRAFT)
            {
                throw new Exception("Event status can not be updated!!!");
            }
            else if(eventStatus==EventStatus.PUBLISHED && event.getStatus()!=EventStatus.DRAFT){
                throw new Exception("Event can not be published!!!");
            }
            else if((eventStatus==EventStatus.STARTED && event.getStatus()!=EventStatus.PUBLISHED)){
                throw new Exception("Event can not be started!!!");
            }
            else if((eventStatus==EventStatus.CLOSED && event.getStatus()!=EventStatus.STARTED)){
                throw new Exception("Event can not be closed!!!");
            }

            event.setStatus(eventStatus);
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

        User user = RequestUtil.getUserLogin(null)
                .orElseThrow(() -> new NoSuchElementException("User not logged in. Please log in to continue."));

        if (!user.getRole().equals(UserRole.ADMIN) && !event.getCreatedBy().getId().equals(user.getId())) {
            throw new NoSuchElementException("You do not have permission to access this event.");
        }

        if(event.getStatus()!=EventStatus.DRAFT){
            throw new Exception("Event can not be deleted");
        }

        eventRepository.delete(event);
        return EventDtoMapper.dtoMapper.eventToEventResponseDto(event);
    }

    @Override
    public List<UpcomingEventResponseDto> findAllUpcomingPublishedEvent() {
        List<Event> publishedEvent=eventRepository.findAllUpcomingPublishedEvent();
        return publishedEvent.stream()
                .map(EventDtoMapper.dtoMapper::eventToUpcomingEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UpcomingEventResponseDto> findAllRunningEvent() {
        List<Event> publishedEvent=eventRepository.findAllRunningEvent();
        return publishedEvent.stream()
                .map(EventDtoMapper.dtoMapper::eventToUpcomingEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void joinEvent(Long eventId) {
        Event event=eventRepository.findById(eventId)
                .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

        StringBuilder sb=new StringBuilder();
        if(event.getStatus()!= EventStatus.STARTED){
            sb.append("Event has not started by host");
        }
//        else if(event.getEventDateTime().isAfter(LocalDateTime.now())){
//            sb.append("you cannot join this event");
//        }
        if(!sb.isEmpty())
            throw new NoSuchElementException(sb.toString());

        User user= RequestUtil.getUserLogin(null)
                .orElseThrow(()->new NoSuchElementException("User not loggedIn!!!"));

        EventAttendance attendance=new EventAttendance();
        attendance.setUser(user);
        attendance.setEvent(event);
        attendance.setCheckInTime(LocalDateTime.now());
        eventAttendanceRepository.save(attendance);
    }

    @Override
    public List<EventAttendedUserDto> findEventAttendance(Long eventId) {
        Event event=eventRepository.findById(eventId)
                .orElseThrow(()->new NoSuchElementException("Event not found!!!"));
        User user= RequestUtil.getUserLogin(null)
                .orElseThrow(()->new NoSuchElementException("User not loggedIn!!!"));

        if (!user.getRole().equals(UserRole.ADMIN) && !event.getCreatedBy().getId().equals(user.getId())) {
            throw new NoSuchElementException("You do not have permission to access this operation.");
        }

        List<User> attendance=eventAttendanceRepository.findEventAttendanceByEventId(eventId);
        return attendance.stream()
                .map(EventDtoMapper.dtoMapper::userToEventAttendedUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventResponseDto> findAllEventsByUserId(Long userId, EventFilterDto filterDto) {
        List<Event> events = eventRepository.findAllEventsByUserId(userId,filterDto);
        List<EventResponseDto> responseDtos = events.stream()
                .map(a->{
                    EventResponseDto responseDto = EventDtoMapper.dtoMapper.eventToEventResponseDto(a);
                    return responseDto;
                })
                .collect(Collectors.toList());

        return responseDtos;
    }

}
