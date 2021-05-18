package com.dsmupgrade.domain.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Embeddable
public class PersonalHomeworkPk implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homework_id")
    private Integer homeworkId;

    @Column(name = "student_username")
    private String studentUsername;

    public PersonalHomeworkPk(String studentUsername){
        this.studentUsername = studentUsername;
    }
}
