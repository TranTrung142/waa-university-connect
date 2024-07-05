package com.miu.waa.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@RequiredArgsConstructor
public class StudentResponseDto {
    private Long id;
    private Long studentId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String profilePictureURL;
    private String major;
    private List<String> academicAchievements;
    private List<String> interests;
    private List<String> extracurricularActivities;
}
