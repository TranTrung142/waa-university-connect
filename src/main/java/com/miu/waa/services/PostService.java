package com.miu.waa.services;

import com.miu.waa.dto.request.PostCreateDto;
import com.miu.waa.dto.response.PostResponseDto;

import java.util.List;

public interface PostService {
    List<PostResponseDto> getPostsByThread(Long threadId);
    PostResponseDto getPostById(Long id);
    PostResponseDto createPost(PostCreateDto postCreateDto);
    PostResponseDto updatePost(Long id, PostCreateDto postCreateDto);
    void deletePost(Long id);
}
