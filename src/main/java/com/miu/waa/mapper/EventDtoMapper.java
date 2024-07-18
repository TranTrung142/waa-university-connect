package com.miu.waa.mapper;

import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.response.*;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.EventAttendance;
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

    @Mapping(target = "createByFullName", ignore = true)
    @Mapping(target = "publishedByFullName", ignore = true)
    EventResponseDto eventToEventResponseDto(Event event);
    UpcomingEventResponseDto eventToUpcomingEventResponseDto(Event event);
    EventAttandenceResponseDto eventAttandenceToResponseDto(EventAttendance eventAttendance);
    EventAttendedUserDto userToEventAttendedUserDto(User user);
}
