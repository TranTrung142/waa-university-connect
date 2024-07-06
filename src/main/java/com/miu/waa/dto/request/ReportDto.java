package com.miu.waa.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {
    private String reportReason;
    private Long reportedUserId;
}
