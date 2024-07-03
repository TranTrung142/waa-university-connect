package com.miu.waa.controllers;

import com.miu.waa.dto.ErrorResponse;
import com.miu.waa.dto.SuccessResponse;
import com.miu.waa.dto.request.ResourceRequest;
import com.miu.waa.entities.Resource;
import com.miu.waa.entities.ResourceType;
import com.miu.waa.entities.User;
import com.miu.waa.mapper.ResourceDtoMapper;
import com.miu.waa.services.ResourceService;
import com.miu.waa.utils.RequestUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService resourceService;
//    private final ResourceDtoMapper resourceDtoMapper;

    @PostMapping
    public ResponseEntity<?> createResource(@RequestBody ResourceRequest resource, HttpServletRequest request) {
        Optional<User> userLogin = RequestUtil.getUserLogin(request);
        Resource newResource = ResourceDtoMapper.dtoMapper.toResource(resource);
        userLogin.ifPresent(user -> newResource.setCreatorId(user.getId()));
        return ResponseEntity.ok(new SuccessResponse(resourceService.saveResource(newResource)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getResourceById(@PathVariable Long id) {
        Optional<Resource> resource = resourceService.getResourceById(id);
        if (resource.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "Resource not found!", null));
        }
        return ResponseEntity.ok(new SuccessResponse(resource));
    }

    @GetMapping
    public ResponseEntity<?> getResources(@RequestParam @Nullable ResourceType resourceType) {
        if (resourceType == null) {
            return ResponseEntity.ok(new SuccessResponse(resourceService.findAllResources()));
        }
        return ResponseEntity.ok(new SuccessResponse(resourceService.findAllByResourceType(resourceType)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResource(@PathVariable Long id, @RequestBody ResourceRequest resource) {
        Resource updatedResource = resourceService.updateResource(id, ResourceDtoMapper.dtoMapper.toResource(resource));
        if (updatedResource == null) {
            return ResponseEntity.status(404).body(new ErrorResponse(404, "Resource not found!", null));
        }
        return ResponseEntity.ok(new SuccessResponse(updatedResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.ok(new SuccessResponse("Resource deleted successfully!"));
    }
}
