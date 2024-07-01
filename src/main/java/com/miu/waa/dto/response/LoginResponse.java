package com.miu.waa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
