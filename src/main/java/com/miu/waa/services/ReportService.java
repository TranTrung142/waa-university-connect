package com.miu.waa.services;

import com.miu.waa.dto.request.ReportDto;
import com.miu.waa.entities.Report;
import com.miu.waa.entities.ReportStatus;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    public Report createReport(ReportDto reportDto, Long userId);
    public List<Report> getReportsByUserId(Long userId);
    public Optional<Report> getReportById(Long id);
    public void deleteReport(Long id);
    public List<Report> searchReports(ReportStatus status);
    public Report approveReport(Long id);
}
