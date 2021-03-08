package com.dsmupgrade.domain.authority.dto.response;


import com.dsmupgrade.domain.student.domain.Student;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ListStudentResponse {
    private String name;
    private String username;
    private Integer fieldId;
    private String studentNum;

    public static ListStudentResponse of(Student student) {
        return ListStudentResponse.builder()
                .fieldId(student.getField().getId())
                .name(student.getName())
                .username(student.getUsername())
                .studentNum(student.getStudentNum())
                .build();
    }
}

