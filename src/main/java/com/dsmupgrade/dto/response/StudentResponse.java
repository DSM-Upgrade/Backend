package com.dsmupgrade.dto.response;

import com.dsmupgrade.domain.entity.Student;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class StudentResponse {

    private final String name;

    private final String studentNum;

    private final String username;

    private final String field;

    public static StudentResponse of(Student student) {
        return StudentResponse.builder()
                .name(student.getName())
                .studentNum(student.getStudentNum())
                .username(student.getUsername())
                .field(student.getField().getName())
                .build();
    }
}
