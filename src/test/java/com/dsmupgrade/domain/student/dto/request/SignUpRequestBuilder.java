package com.dsmupgrade.domain.student.dto.request;

import com.dsmupgrade.domain.student.domain.Student;

public class SignUpRequestBuilder {

    public static SignUpRequest build(String username, String password, String studentNum, String name, int fieldId)  {
        return SignUpRequest.builder()
                .username(username)
                .password(password)
                .studentNum(studentNum)
                .name(name)
                .fieldId(fieldId)
                .build();
    }

    public static SignUpRequest buildFrom(Student student) {
        return SignUpRequest.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .studentNum(student.getStudentNum())
                .name(student.getName())
                .fieldId(student.getField().getId())
                .build();
    }
}
