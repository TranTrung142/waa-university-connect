package com.miu.waa.services.impl;

import com.miu.waa.entities.Resource;
import com.miu.waa.entities.ResourceType;
import com.miu.waa.repositories.ResourceRepository;
import com.miu.waa.services.ResourceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;

    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }

    public Resource updateResource(Long id, Resource resource) {
        return resourceRepository.findById(id).map( foundResource -> {
            resource.setId(id);
            resource.setCreatorId(foundResource.getCreatorId());
            resource.setCreatedAt(foundResource.getCreatedAt());
            return resourceRepository.save(resource);
        }).orElse(null);
    }

    public List<Resource> findAllByResourceType(ResourceType resourceType) {
        return resourceRepository.findAllByResourceType(resourceType);
    }

    public List<Resource> findAllResources() {
        return resourceRepository.findAll();
    }
}
