package com.miu.waa.mapper;

import com.miu.waa.dto.EventDto;
import com.miu.waa.dto.UserDto;
import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.request.LiveMessageCreateDto;
import com.miu.waa.dto.response.*;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventAttendance;
import com.miu.waa.entities.LiveMessage;
import com.miu.waa.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventDtoMapper {
    EventDtoMapper dtoMapper =
            Mappers.getMapper(EventDtoMapper.class);

    @Mapping(target = "createdBy", ignore = true)
    Event eventCreateDtoToEvent(EventCreateDto eventCreateDto);

    EventResponseDto eventToEventResponseDto(Event event);
    UpcomingEventResponseDto eventToUpcomingEventResponseDto(Event event);
    EventAttandenceResponseDto eventAttandenceToResponseDto(EventAttendance eventAttendance);
    EventAttendedUserDto userToEventAttendedUserDto(User user);
    UserDto userToCreatedByUserDto(User user);
    EventDto eventToEventDto(Event event);
    LiveMessageResponseDto liveMessageToResponseDto(LiveMessage liveMessage);
    LiveMessage liveMessageCreateDtoToLiveMessage(LiveMessageCreateDto liveMessageCreateDto);
}
