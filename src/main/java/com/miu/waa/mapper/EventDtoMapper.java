package com.miu.waa.mapper;

import com.miu.waa.dto.request.EventCreateDto;
import com.miu.waa.dto.response.EventResponseDto;
import com.miu.waa.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventDtoMapper {
    EventDtoMapper dtoMapper =
            Mappers.getMapper(EventDtoMapper.class);

    @Mapping(target = "organizer", ignore = true)
    Event eventCreateDtoToEvent(EventCreateDto eventCreateDto);
    EventResponseDto eventToEventResponseDto(Event event);
}
