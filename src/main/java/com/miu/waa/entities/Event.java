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
    @JoinColumn(name = "user_id")
    private User organizer;

    @Enumerated(EnumType.STRING)
    private EventStatus status;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
}
