package com.dsmupgrade.domain.homework.domain;

import lombok.*;

import javax.persistence.*;
import java.lang.invoke.StringConcatFactory;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonalHomework {
    @Id
    @Column(name = "homework_id")
    private String studentHomeworkId;

    @Column(nullable = false)
    private String studentUsername;

    @Setter
    @Column(nullable = false)
    private PersonalHomeworkStatus status;

    @Column(nullable = true)
    private LocalDateTime submittedAt;

    @Column(nullable = true)
    private String content;

    @ManyToOne
    private Homework homework;

    @OneToMany
    private List<PersonalHomeworkFile> personalHomeworkFile;
}