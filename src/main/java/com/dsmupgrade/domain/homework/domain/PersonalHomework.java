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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String studentUsername;

    @Column(nullable = false)
    private PersonalHomeworkStatus status; // 과제를 제출해서 완료가 되었는지

    @Column(nullable = true)
    private Date submittedAt;

    @Column(nullable = true)
    private String content;

    @ManyToOne
    private Homework homework;
}