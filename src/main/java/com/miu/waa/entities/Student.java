package com.miu.waa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Student extends User {
    private Long studentId;
    private String major;

    @ElementCollection
    private List<String> academicAchievements;

    @ElementCollection
    private List<String> interests;

    @ElementCollection
    private List<String> extracurricularActivities;
}
