package com.miu.waa.mapper;
import com.miu.waa.dto.request.ThreadCreateDto;
import com.miu.waa.dto.response.ThreadResponseDto;
import com.miu.waa.entities.DiscussionCategory;
import com.miu.waa.entities.Thread;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ThreadDtoMapper {
    ThreadDtoMapper dtoMapper = Mappers.getMapper(ThreadDtoMapper.class);
    ThreadResponseDto threadToThreadResponseDto(Thread thread);
    Thread threadCreateDtoToThread(ThreadCreateDto thread);
}
