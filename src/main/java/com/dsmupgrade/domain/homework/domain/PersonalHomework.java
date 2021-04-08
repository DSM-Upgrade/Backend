package com.dsmupgrade.domain.homework.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonalHomework {
    @Id
    private String studentHomeworkId;

    @Column(nullable = false)
    private String studentUsername;

    @Column(nullable = false)
    private PersonalHomeworkStatus status;

    @Column(nullable = true)
    private LocalDateTime submittedAt;

    @Column(nullable = true)
    private String content;

    @ManyToOne
    private Homework homework;
}