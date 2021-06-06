package com.dsmupgrade.domain.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Embeddable
public class HomeworkFilePk implements Serializable {
    @Column(name = "homework_id")
    private Integer homeworkId;

    @Column(name = "username")
    private String studentUsername;
}
