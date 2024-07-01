package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.PostCreateDto;
import com.miu.waa.dto.response.PostResponseDto;
import com.miu.waa.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/thread/{threadId}")
    public ResponseEntity<?> getPostsByThreadId(@PathVariable Long threadId) {
        try {
            return ResponseEntity.ok(new SuccessResponse(postService.getPostsByThread(threadId)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            PostResponseDto post = postService.getPostById(id);
            if (post == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "Post not found", null));
            }

            return ResponseEntity.ok(new SuccessResponse(post));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostCreateDto postDto) {
        try {
            PostResponseDto post = postService.createPost(postDto);
            if (post == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "Post not found", null));
            }

            return ResponseEntity.ok(new SuccessResponse(postService.createPost(postDto)));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostCreateDto postDto) {
        try {
            PostResponseDto post = postService.updatePost(id, postDto);
            if (post == null) {
                return ResponseEntity.status(404)
                        .body(new ErrorResponse(404, "Post not found", null));
            }
            return ResponseEntity.ok(new SuccessResponse(post));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.ok(new SuccessResponse("Post deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ErrorResponse(500, "Internal server error", e.getMessage()));
        }
    }
}
