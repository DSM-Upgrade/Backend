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
public class HomeworkFilePk implements Serializable {

    @EqualsAndHashCode.Include
    private PersonalHomeworkPk personalHomeworkPk;

    @EqualsAndHashCode.Include
    @Column(name = "name")
    private String name;
}
