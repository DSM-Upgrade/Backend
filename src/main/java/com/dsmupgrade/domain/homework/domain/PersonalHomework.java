package com.dsmupgrade.domain.homework.domain;

import lombok.*;

import javax.persistence.*;
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
    private Date submittedAt;

    @Column(nullable = true)
    private String content;

    @ManyToOne
    private Homework homework;
}