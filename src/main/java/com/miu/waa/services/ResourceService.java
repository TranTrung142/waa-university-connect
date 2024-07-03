package com.miu.waa.services;

import com.miu.waa.entities.Resource;
import com.miu.waa.entities.ResourceType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ResourceService {
    public Optional<Resource> getResourceById(Long id);
    public Resource saveResource(Resource resource);
    public void deleteResource(Long id);
    public Resource updateResource(Long id, Resource resource);
    public List<Resource> findAllByResourceType(ResourceType resourceType);
    public List<Resource> findAllResources();
}
