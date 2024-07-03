package com.miu.waa.services.impl;

import com.miu.waa.dto.request.ReportDto;
import com.miu.waa.entities.Report;
import com.miu.waa.entities.ReportStatus;
import com.miu.waa.entities.User;
import com.miu.waa.entities.UserStatus;
import com.miu.waa.repositories.ReportRepository;
import com.miu.waa.services.ReportService;
import com.miu.waa.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserService userService;

    public Report createReport(ReportDto reportDto, Long userId) {
        Report report = new Report();
        report.setReportReason(reportDto.getReportReason());
        report.setReporter(userService.getUserById(userId));
        report.setReportedUser(userService.getUserById(reportDto.getReportedUserId()));
        return reportRepository.save(report);
    }

    public List<Report> getReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    public List<Report> searchReports(ReportStatus status) {
        return reportRepository.findAllByStatus(status);
    }

    @Transactional(rollbackOn = Exception.class)
    public Report approveReport(Long id) {
        try {
            Optional<Report> report = reportRepository.findById(id);
            if (report.isEmpty()) {
                return null;
            }
            report.get().setStatus(ReportStatus.APPROVED);

            User reportedUser = report.get().getReportedUser();
            reportedUser.setStatus(UserStatus.BLOCKED);
            return report.get();
        } catch (Exception e) {
            throw new RuntimeException("Error while approving report");
        }
    }
}
