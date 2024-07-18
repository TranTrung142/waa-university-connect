package com.miu.waa.dto.request;

import com.miu.waa.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LiveMessageCreateDto {
    private Long eventId;

    private Long userId;

    private String message;
}
