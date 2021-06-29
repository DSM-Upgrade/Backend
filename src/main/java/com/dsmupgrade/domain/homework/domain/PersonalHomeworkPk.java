package com.dsmupgrade.domain.homework.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PersonalHomeworkPk implements Serializable {

    @EqualsAndHashCode.Include
    @Column(name = "homework_id")
    private int homeworkId;

    @EqualsAndHashCode.Include
    @Column(name = "student_username")
    private String studentUsername;
}
