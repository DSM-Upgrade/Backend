package com.dsmupgrade.domain.student.dto.response;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.field.dto.response.FieldResponse;
import com.dsmupgrade.domain.student.domain.Student;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class StudentResponse {

    private final String name;

    private final String studentNum;

    private final String username;

    private final FieldResponse field;

    private final String profileImageName;

    public static StudentResponse from(Student student) {
        return StudentResponse.builder()
                .name(student.getName())
                .studentNum(student.getStudentNum())
                .username(student.getUsername())
                .field(FieldResponse.from(student.getField()))
                .profileImageName(student.getProfileImageName())
                .build();
    }
}
