package com.miu.waa.dto.response;

import com.miu.waa.entities.DiscussionCategory;
import com.miu.waa.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ThreadResponseDto {
    private Long id;
    private String title;
    private DiscussionCategory category;
    private User createdBy;
}
