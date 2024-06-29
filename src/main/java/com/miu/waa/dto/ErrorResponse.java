package com.miu.waa.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private Object errors;

    public ErrorResponse(int status, String message, Object errors) {
        this.message = message;
        this.errors = errors;
        this.status = status;
    }
}
