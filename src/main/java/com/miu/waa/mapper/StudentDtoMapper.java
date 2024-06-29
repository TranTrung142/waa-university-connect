package com.miu.waa.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentDtoMapper {
    StudentDtoMapper dtoMapper =
            Mappers.getMapper(StudentDtoMapper.class);


}
