package com.miu.waa.repositories;

import com.miu.waa.entities.Report;
import com.miu.waa.entities.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByStatus(ReportStatus status);
    List<Report> findAllByReporterId(Long userId);
}
