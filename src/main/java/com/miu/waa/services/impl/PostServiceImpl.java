package com.miu.waa.services.impl;

import com.miu.waa.dto.request.PostCreateDto;
import com.miu.waa.dto.response.PostResponseDto;
import com.miu.waa.entities.Post;
import com.miu.waa.mapper.PostDtoMapper;
import com.miu.waa.repositories.PostRepository;
import com.miu.waa.repositories.ThreadRepository;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.search_spec.PostSpecificationsBuilder;
import com.miu.waa.search_spec.SearchOperation;
import com.miu.waa.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @Override
    public Page<Post> search(int page, int size, boolean save, String search) {
        // Insert the search history into the database
//        SearchHistory searchHistory = new SearchHistory();
//        searchHistory.setSearchCriteria(search);
//        searchHistory.setUser(userRepository.findById(1L).get());
//        searchHistory.setCreatedAt(LocalDateTime.now());
//        searchHistory.setUpdatedAt(LocalDateTime.now());
//        searchHistoryRepository.save(searchHistory);

        PostSpecificationsBuilder builder = new PostSpecificationsBuilder();
        String operationSetExper = String.join("|", SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }
        // Add custom condition to check deletedAt is not null
        builder.withDeletedAtIsNull();

//        // Add custom condition to check deletedAt is not null
//        builder.with(null, "deletedAt", "n", null, "", "");

        Specification<Post> spec = builder.build();
        return postRepository.findAll(spec, PageRequest.of(page, size));
    }
}
