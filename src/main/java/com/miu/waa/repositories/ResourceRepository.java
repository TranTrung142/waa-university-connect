package com.miu.waa.repositories;

import com.miu.waa.entities.Resource;
import com.miu.waa.entities.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findAllByResourceType(ResourceType resourceType);
}
