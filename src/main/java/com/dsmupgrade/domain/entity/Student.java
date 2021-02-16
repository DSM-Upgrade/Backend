package com.dsmupgrade.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @Column(length = 16, nullable = false)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 4, nullable = false)
    private String studentNum;

    @Column(nullable = false)
    private Boolean isAdmin;

    @Column(nullable = false)
    private Boolean isRegistered;

    @Column(nullable = false)
    private Boolean isFineManager;

    @Column(nullable = false)
    private Boolean isNoticeManager;

    @Column(length = 4, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @Builder
    public Student(String username, String password, String studentNum, String name, Field field) {
        setUsername(username);
        setPassword(password);
        setStudentNum(studentNum);
        setName(name);
        setField(field);
        setIsAdmin(false);
        setIsRegistered(false);
        setIsFineManager(false);
        setIsNoticeManager(false);
    }

    public void register() {
        setIsRegistered(true);
    }

    public void updatePassword(String password) {
        setPassword(password);
    }

    public void updateStudentNum(String studentNum) {
        setStudentNum(studentNum);
    }

    public void updateField(Field field) {
        setField(field);
    }
}
