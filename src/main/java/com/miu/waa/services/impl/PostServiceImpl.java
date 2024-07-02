package com.miu.waa.services.impl;

import com.miu.waa.dto.request.PostCreateDto;
import com.miu.waa.dto.response.PostResponseDto;
import com.miu.waa.entities.Post;
import com.miu.waa.mapper.PostDtoMapper;
import com.miu.waa.repositories.PostRepository;
import com.miu.waa.repositories.ThreadRepository;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    @Override
    public List<PostResponseDto> getPostsByThread(Long threadId) {
        List<Post> posts = postRepository.findByThreadIdOrderByCreatedAtAsc(threadId);
        return posts.stream()
                .map(PostDtoMapper.dtoMapper::postToPostResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponseDto getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return PostDtoMapper.dtoMapper.postToPostResponseDto(post.get());
        }
        throw new NoSuchElementException("Post not found");
    }

    @Override
    public PostResponseDto createPost(PostCreateDto postCreateDto) {
        Post post = PostDtoMapper.dtoMapper.postCreateDtoToPost(postCreateDto);
        post.setUser(userRepository.findById(postCreateDto.getCreatedById())
                .orElseThrow(() -> new NoSuchElementException("User not found")));
        post.setThread(threadRepository.findById(postCreateDto.getThreadId())
                .orElseThrow(() -> new NoSuchElementException("Thread not found")));
        post = postRepository.save(post);
        return PostDtoMapper.dtoMapper.postToPostResponseDto(post);
    }

    @Override
    public PostResponseDto updatePost(Long id, PostCreateDto postCreateDto) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post postToUpdate = post.get();
            postToUpdate.setContent(postCreateDto.getContent());
            Post updatedPost = postRepository.save(postToUpdate);
            return PostDtoMapper.dtoMapper.postToPostResponseDto(updatedPost);
        }
        throw new NoSuchElementException("Post not found");
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
