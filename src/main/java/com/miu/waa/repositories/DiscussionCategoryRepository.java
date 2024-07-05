package com.miu.waa.repositories;

import com.miu.waa.entities.DiscussionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionCategoryRepository extends JpaRepository<DiscussionCategory, Long> {
}

