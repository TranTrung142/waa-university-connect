package com.miu.waa.mapper;

import com.miu.waa.dto.UserDto;
import com.miu.waa.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDtoMapper {
    UserDtoMapper dtoMapper = Mappers.getMapper(UserDtoMapper.class);

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
