package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.dto.response.UpcomingEventResponseDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.Student;
import com.miu.waa.entities.User;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.services.EventService;
import com.miu.waa.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            EventResponseDto eventResponseDto = eventService.findById(id);
            if (eventResponseDto == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "Event not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(eventResponseDto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDto eventCreateDto,HttpServletRequest request) {
        try {
            Event event= EventDtoMapper.dtoMapper.eventCreateDtoToEvent(eventCreateDto);
            Optional<User> userLogin = RequestUtil.getUserLogin(request);
            userLogin.ifPresent(user -> event.setCreatedBy(user));
            return ResponseEntity.ok(new SuccessResponse(eventService.save(event)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventCreateDto eventCreateDto, HttpServletRequest request) {
        try {
            return ResponseEntity.ok(new SuccessResponse(eventService.update(id,eventCreateDto)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
            eventService.delete(id);
            return ResponseEntity.ok(new SuccessResponse("Event deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,@RequestParam  EventStatus status) {
        try {
            eventService.updateStatus(id, status);
            return ResponseEntity.ok(new SuccessResponse("Status updated successfully!!!"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
    @GetMapping("/upcoming-events")
    public ResponseEntity<?> getAllUpcomingEvents() {
        List<UpcomingEventResponseDto> result=eventService.findAllUpcomingPublishedEvent();
        return ResponseEntity.ok(result);
    }
    @PostMapping("/{eventId}/join-event")
    public ResponseEntity<?> joinEvent(@PathVariable Long eventId) {
        try {
            eventService.joinEvent(eventId);
            return ResponseEntity.ok(new SuccessResponse("Joined successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("/{eventId}/attendance")
    public ResponseEntity<?> getAttandence(@PathVariable Long eventId) {
        try {
            return ResponseEntity.ok(eventService.findEventAttendance(eventId));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getCurrentUserEvent(/*@RequestParam(required = false) EventFilterDto filterDto*/){
        try {
            EventFilterDto filterDto=null;
            if (filterDto == null) {
                filterDto = new EventFilterDto();
                filterDto.setStatus(null); // or set a default value
                filterDto.setDate(null);   // or set a default value
            }
            Long userId=RequestUtil.getUserLogin(null).get().getId();
            List<EventResponseDto> eventResponseDtos = eventService.findAllEventsByUserId(userId,filterDto);
            return ResponseEntity.ok(new SuccessResponse(eventResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
}
