package com.dsmupgrade.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PersonalHomeworkPk implements Serializable {
    private String studentUsername;
    private Integer homeworkId;
}
