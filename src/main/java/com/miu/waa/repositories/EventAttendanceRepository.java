package com.miu.waa.repositories;

import com.miu.waa.entities.EventAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Long> {
}
