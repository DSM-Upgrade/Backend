package com.dsmupgrade.domain.field.domain;

import com.dsmupgrade.domain.student.domain.Student;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "field")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15, nullable = false)
    private String name;

    @OneToMany(mappedBy = "field")
    private List<Student> students;

    @Builder
    public Field(int id, String name) {
        this.id = id;
        this.name = name;
        this.students = null;
    }
}
