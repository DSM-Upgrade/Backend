package com.dsmupgrade.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@IdClass(value = PersonalHomeworkPk.class)
public class PersonalHomework {
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