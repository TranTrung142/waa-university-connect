package com.miu.waa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Student extends User{
    private Long studentId;
    private String major;

    @ElementCollection
    private List<String> academicAchievements;

    @ElementCollection
    private List<String> interests;

    @ElementCollection
    private List<String> extracurricularActivities;
}
