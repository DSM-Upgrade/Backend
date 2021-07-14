package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkContentAdminResponse {
    private String content;

    public static HomeworkContentAdminResponse from(PersonalHomework personalHomework) {
        return HomeworkContentAdminResponse.builder()
                .content(personalHomework.getContent())
                .build();
    }
}
