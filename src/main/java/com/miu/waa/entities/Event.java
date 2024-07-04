package com.miu.waa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String description;

    private LocalDateTime eventDateTime;

    @ManyToOne
    @JoinColumn
    private User createdBy;
    private LocalDateTime createdOn;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @ManyToOne
    @JoinColumn
    private User approvedBy;
    private LocalDateTime approvedOn;
}
