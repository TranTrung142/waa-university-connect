package com.miu.waa.services.impl;

import com.miu.waa.dto.request.LiveMessageCreateDto;
import com.miu.waa.dto.response.LiveMessageResponseDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventStatus;
import com.miu.waa.entities.LiveMessage;
import com.miu.waa.entities.User;
import com.miu.waa.mapper.EventDtoMapper;
import com.miu.waa.repositories.EventRepository;
import com.miu.waa.repositories.LiveMessageRepository;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.services.LiveMessageService;
import com.miu.waa.utils.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LiveMessageServiceImpl implements LiveMessageService {
    private final LiveMessageRepository liveMessageRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    @Override
    public LiveMessageResponseDto createLiveMessage(LiveMessageCreateDto dto) throws Exception {
        try{
//            User user = userRepository.findById(dto.getUserId())
//                    .orElseThrow(() -> new NoSuchElementException("User not found!!"));

            User user = RequestUtil.getUserLogin(null)
                    .orElseThrow(() -> new NoSuchElementException("User not found!!"));

            Event event=eventRepository.findById(dto.getEventId())
                    .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

            if(event.getStatus()!=EventStatus.STARTED){
                throw new Exception("Event has not started!!!");
            }

            LiveMessage liveMessage = EventDtoMapper.dtoMapper.liveMessageCreateDtoToLiveMessage(dto);
            liveMessage.setTimestamp(LocalDateTime.now());
            liveMessage.setUser(user);
            liveMessage.setEvent(event);
            liveMessage = liveMessageRepository.save(liveMessage);
            return EventDtoMapper.dtoMapper.liveMessageToResponseDto(liveMessage);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<LiveMessageResponseDto> getAllLiveMessages(Long eventId) throws Exception {
        try{
            Event event=eventRepository.findById(eventId)
                    .orElseThrow(()->new NoSuchElementException("Event not found!!!"));

            if(event.getStatus()!=EventStatus.STARTED){
                throw new Exception("Event has not started!!!");
            }

            List<LiveMessage> events=liveMessageRepository.findAllByEventId(eventId);
            return events.stream()
                    .map(EventDtoMapper.dtoMapper::liveMessageToResponseDto)
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            throw e;
        }
    }
}
