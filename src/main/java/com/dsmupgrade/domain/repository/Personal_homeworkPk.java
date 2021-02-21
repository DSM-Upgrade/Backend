package com.dsmupgrade.domain.repository;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Personal_homeworkPk implements Serializable {
    private String studentUsername;
    private Integer homeworkId;
}
