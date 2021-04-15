package com.dsmupgrade.domain.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonalHomeworkFile {
    @Id
    private Integer id;
    private String name;
}
