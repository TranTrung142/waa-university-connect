package com.miu.waa.dto.response;

import com.miu.waa.entities.Thread;
import com.miu.waa.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;
    private Thread thread;
    private User user;
    private Long parentPostId;
    //private List<Post> replies;
}
