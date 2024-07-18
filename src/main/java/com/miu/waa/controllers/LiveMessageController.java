package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.request.LiveMessageCreateDto;
import com.miu.waa.dto.response.LiveMessageResponseDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.LiveMessage;
import com.miu.waa.entities.User;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.services.LiveMessageService;
import com.miu.waa.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/live-message")
@RequiredArgsConstructor
public class LiveMessageController {
    private final LiveMessageService liveMessageService;
    @PostMapping
    public ResponseEntity<?> createLiveMessage(@RequestBody LiveMessageCreateDto createDto, HttpServletRequest request) {
        try {
            LiveMessageResponseDto liveMessage = liveMessageService.createLiveMessage(createDto);
            return ResponseEntity.ok(new SuccessResponse(liveMessage));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getLiveMessage(@PathVariable Long eventId) {
        try {
            List<LiveMessageResponseDto> liveMessageResponseDtos=liveMessageService.getAllLiveMessages(eventId);
            return ResponseEntity.ok(new SuccessResponse(liveMessageResponseDtos));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, e.getMessage(), null));
        }
    }
}
