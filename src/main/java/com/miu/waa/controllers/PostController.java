package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.PostCreateDto;
import com.miu.waa.dto.response.PostResponseDto;
import com.miu.waa.entities.Post;
import com.miu.waa.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

    /**
     * Search query can be a combination of the following:
     * Equality: represented by colon (:)
     * Negation: represented by Exclamation mark (!)
     * Greater than: represented by (>)
     * Less than: represented by (<)
     * Like: represented by tilde (~)
     * Starts with: represented by (:prefix*)
     * Ends with: represented by (:*suffix)
     * Contains: represented by (:*substring*)
     *
     * @param search
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<?> getAllProperties(@RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("search") String search) {
        try {
            Page<Post> result = postService.search(page, size, true, search);
            if (page > result.getTotalPages()) {
                return new ResponseEntity<>("Page not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
