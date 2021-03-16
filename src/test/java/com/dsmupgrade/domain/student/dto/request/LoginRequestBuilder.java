package com.dsmupgrade.domain.student.dto.request;

import com.dsmupgrade.domain.student.domain.Student;

public class LoginRequestBuilder {

    public static LoginRequest build(String username, String password) {
        return LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
    }

    public static LoginRequest buildFrom(Student student) {
        return LoginRequest.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .build();
    }
}
