package com.miu.waa.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ThreadResponseDto {
    private Long id;
    private String title;
    private Long categoryId;
    private Long createdById;
}
