package com.miu.waa.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class SuccessResponse {
    private int status;
    private String message;
    private Object data;

    public SuccessResponse(Object data) {
        this.data = data;
        this.status = 200;
        this.message = "Success";
    }
}
