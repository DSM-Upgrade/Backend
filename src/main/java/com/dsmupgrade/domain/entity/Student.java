package com.dsmupgrade.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}
