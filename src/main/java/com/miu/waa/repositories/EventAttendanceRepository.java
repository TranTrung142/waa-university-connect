package com.miu.waa.repositories;

import com.miu.waa.entities.EventAttendance;
import com.miu.waa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Long> {

    @Query("select distinct e.user from EventAttendance e where e.event.id = :eventId")
    List<User> findEventAttendanceByEventId(Long eventId);
}
