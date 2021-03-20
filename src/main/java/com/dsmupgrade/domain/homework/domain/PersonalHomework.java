package com.dsmupgrade.domain.homework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(value = PersonalHomeworkPk.class)
public class PersonalHomework {
    @Id
    @Column(nullable = false)
    private String studentUsername;

    @Column(nullable = false)
    private PersonalHomeworkStatus status; // 과제를 제출해서 완료가 되었는지

    @Column(nullable = true)
    private Date submittedAt;

    @Column(nullable = false)
    private String content;

    @Id
    @Column(nullable = false)
    private Integer homeworkId;

    @JoinColumn(name = "homework_id", referencedColumnName = "id")
    @ManyToOne
    private Homework homework;
}