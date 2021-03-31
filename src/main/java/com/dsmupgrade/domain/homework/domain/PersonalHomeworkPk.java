package com.dsmupgrade.domain.homework.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalHomeworkPk implements Serializable {
    private String studentUsername;
    private Homework homework;
}
