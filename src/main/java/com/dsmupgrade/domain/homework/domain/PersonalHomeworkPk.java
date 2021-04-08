package com.dsmupgrade.domain.homework.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class PersonalHomeworkPk implements Serializable {
    private String studentUsername;
    private Integer homeworkId;
}
