package com.dsmupgrade.domain.homework.domain;

import com.dsmupgrade.domain.student.domain.Student;
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
@Entity(name = "personal_homework")
public class PersonalHomework {
    @EmbeddedId
    private PersonalHomeworkPk id;

    @Setter
    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private PersonalHomeworkStatus status;

    @Column(nullable = true, name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(nullable = true, name = "content")
    private String content;

    @ManyToOne
    @MapsId("homeworkId")
    @JoinColumn(name = "homework_id")
    private Homework homework;

    @ManyToOne
    @MapsId("studentUsername")
    @JoinColumn(name = "student_username")
    private Student student;

    @Setter
    @OneToMany(mappedBy = "personalHomework")
    private List<HomeworkFile> homeworkFile;
}