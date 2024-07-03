package com.miu.waa.mapper;

import com.miu.waa.dto.request.PostCreateDto;
import com.miu.waa.dto.response.PostResponseDto;
import com.miu.waa.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostDtoMapper {
    PostDtoMapper dtoMapper = Mappers.getMapper(PostDtoMapper.class);
    @Mapping(source = "parentPost.id", target = "parentPostId")
    PostResponseDto postToPostResponseDto(Post post);
    Post postCreateDtoToPost(PostCreateDto post);
}
