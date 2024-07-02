package com.miu.waa.mapper;

import com.miu.waa.dto.request.ResourceRequest;
import com.miu.waa.entities.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourceDtoMapper {
    ResourceDtoMapper dtoMapper = Mappers.getMapper(ResourceDtoMapper.class);

    Resource toResource(ResourceRequest resourceRequest);

}
