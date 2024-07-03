package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.Student;
import com.miu.waa.entities.User;
import com.miu.waa.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @GetMapping
    public ResponseEntity<?> getAllEvent() {
        try {
            return ResponseEntity.ok(new SuccessResponse(eventService.findAll()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
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
    public ResponseEntity<?> createEvent(@RequestBody EventCreateDto eventCreateDto) {
        try {
            return ResponseEntity.ok(new SuccessResponse(eventService.save(eventCreateDto)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventCreateDto eventCreateDto) {
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
    @PutMapping("/publish/{id}")
    public ResponseEntity<?> publishEvent(@PathVariable Long id) {
        try {
            eventService.updateStatus(id, EventStatus.PUBLISHED);
            return ResponseEntity.ok(new SuccessResponse("Event published successfully!!!"));
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
}
