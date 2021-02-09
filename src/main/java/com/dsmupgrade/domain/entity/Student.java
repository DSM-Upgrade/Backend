package com.dsmupgrade.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer grade;

    @Column(name = "class", nullable = false)
    private Integer cls;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Boolean isAdmin;

    @Column(nullable = false)
    private Boolean isRegistered;

    @Column(length = 4, nullable = false)
    private String name;

    @Column(length = 16)
    private String profile;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Builder
    public Student(String username, String password, int grade, int cls, int number, String name, Field field) {
        setUsername(username);
        setPassword(password);
        setGrade(grade);
        setCls(cls);
        setNumber(number);
        setName(name);
        setField(field);
        setIsAdmin(false);
        setIsRegistered(false);
        setProfile(null);
    }

    public void register() {
        setIsRegistered(true);
    }
}
