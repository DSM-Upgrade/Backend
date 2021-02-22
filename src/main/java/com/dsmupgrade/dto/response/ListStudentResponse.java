package com.dsmupgrade.dto.response;

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
    private Integer studentNum;

//    public static ListStudentResponse of(Student student) {
//        return ListStudentResponse.builder()
//                .fieldId(student.getField)
//                .name(student.getName)
//                .username(student.getUsername)
//                .studentNum(student.getStudentNum)
//                .build();
//    }
}
