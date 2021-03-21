package com.dsmupgrade.domain.student.domain;

import com.dsmupgrade.domain.field.domain.Field;

public class StudentBuilder {

    public static Student build() {
        final String username = "dkssud9556";
        final String password = "$2a$10$Gx4ImiYpJ/Uh/jM5OjIpOOYoPmT0Jk9ZX3dfxaMm3Q2PWx7wT.Fd6";
        final String studentNum = "3401";
        final String name = "김대웅";
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
