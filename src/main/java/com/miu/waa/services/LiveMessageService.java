package com.miu.waa.services;

import com.miu.waa.dto.request.LiveMessageCreateDto;
import com.miu.waa.dto.response.LiveMessageResponseDto;

import java.util.List;

public interface LiveMessageService {
    LiveMessageResponseDto createLiveMessage(LiveMessageCreateDto dto) throws Exception;
    List<LiveMessageResponseDto> getAllLiveMessages(Long eventId) throws Exception;
}
