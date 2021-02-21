package com.dsmupgrade.domain.entity;

import com.dsmupgrade.domain.repository.Personal_homeworkPk;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@IdClass(value = Personal_homeworkPk.class)
public class Personal_homework {
    @Id
    @Column(nullable = false)
    private String studentUsername;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = true)
    private Date submittedAt;

    @Column(nullable = false)
    private String content;

    @Id
    @Column(nullable = false)
    private Integer homeworkId;
}