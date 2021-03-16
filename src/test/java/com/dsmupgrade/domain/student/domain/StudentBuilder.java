package com.dsmupgrade.domain.student.domain;

import com.dsmupgrade.domain.field.domain.Field;

public class StudentBuilder {

    public static Student build() {
        final String username = "dkssud9556";
        final String password = "@Passw0rd";
        final String studentNum = "3401";
        final String name = "김대웅";
        final Field field = fieldBuild();

        return createStudent(username, password, studentNum, name, field);
    }

    public static Student build(String username, String password, String studentNum, String name) {
        final Field field = fieldBuild();
        return createStudent(username, password, studentNum, name, field);
    }

    private static Student createStudent(String username, String password, String studentNum, String name, Field field) {
        return Student.builder()
                .username(username)
                .password(password)
                .studentNum(studentNum)
                .name(name)
                .field(field)
                .build();
    }

    private static Field fieldBuild() {
        return Field.builder()
                .id(1)
                .name("백엔드")
                .build();
    }
}
