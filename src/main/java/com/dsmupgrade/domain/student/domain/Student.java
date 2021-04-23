package com.dsmupgrade.domain.student.domain;

import com.dsmupgrade.domain.field.domain.Field;
import lombok.*;

import javax.persistence.*;
import java.util.function.Consumer;

@Entity
@Setter(AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @Column(length = 16, nullable = false, name = "username")
    private String username;

    @Column(length = 60, nullable = false, name = "password")
    private String password;

    @Column(length = 4, nullable = false, name = "student_num")
    private String studentNum;

    @Column(nullable = false, name = "is_admin")
    private Boolean isAdmin;

    @Column(nullable = false, name = "is_registered")
    private Boolean isRegistered;

    @Column(nullable = false, name = "is_fine_manager")
    private Boolean isFineManager;

    @Column(nullable = false, name = "is_notice_manager")
    private Boolean isNoticeManager;

    @Column(length = 4, nullable = false, name = "name")
    private String name;

    @Column(length = 25, name = "profile_image_name")
    private String profileImageName;

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

    public void noticeManager() {
        setIsNoticeManager(true);
    }

    public void fineManager() {
        setIsFineManager(true);
    }

    public void updatePassword(String password) {
        setIfNotNull(this::setPassword, password);
    }

    public void updateStudentNum(String studentNum) {
        setIfNotNull(this::setStudentNum, studentNum);
    }

    public void updateField(Field field) {
        setIfNotNull(this::setField, field);
    }

    public void updateProfileImageName(String profileImageName) {
        setProfileImageName(profileImageName);
    }

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if (value != null)
            setter.accept(value);
    }
}
