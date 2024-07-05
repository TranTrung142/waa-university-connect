package com.miu.waa.repositories;

import com.miu.waa.entities.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
