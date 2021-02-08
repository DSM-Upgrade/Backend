package com.dsmupgrade.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Field {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 15, nullable = false)
    private String name;

    @OneToMany(mappedBy = "field")
    private List<Student> students;
}
