package com.miu.waa.repositories;

import com.miu.waa.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e " +
            "where e.status='PUBLISHED' and e.eventDateTime>=CURRENT_TIMESTAMP")
    List<Event> findAllUpcomingPublishedEvent();
}
