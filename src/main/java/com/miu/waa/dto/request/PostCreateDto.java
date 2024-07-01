package com.miu.waa.dto.request;

import lombok.*;

@Data
@RequiredArgsConstructor
public class PostCreateDto {
    private String content;
    private Long threadId;
    private Long createdById;
    private Long parentPostId;
}
