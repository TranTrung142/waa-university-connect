package com.miu.waa.repositories;

import com.miu.waa.dto.request.EventFilterDto;
import com.miu.waa.entities.Event;
import com.miu.waa.entities.LiveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiveMessageRepository extends JpaRepository<LiveMessage, Long> {
    @Query("select e from LiveMessage e " +
            "where e.event.id=:eventId"
    )
    List<LiveMessage> findAllByEventId(Long eventId);
}
