package com.miu.waa.repositories;

import com.miu.waa.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByThreadIdOrderByCreatedAtAsc(Long threadId);
}
