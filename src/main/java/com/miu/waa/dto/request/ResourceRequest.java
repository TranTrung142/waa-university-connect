package com.miu.waa.dto.request;

import com.miu.waa.entities.ResourceType;
import lombok.Data;

@Data
public class ResourceRequest {
    private String title;
    private String description;
    private String filePath;
    private ResourceType resourceType;
//    private Long creatorId;
}
