package com.miu.waa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThreadCreateDto {
    private Long id;
    private String title;
    private Long categoryId;
    private Long createdById;
}
