package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkAdminResponse {
    private int id;
    private String username;
    private String title;
    private LocalDateTime createdAt;
    private String status;

    public static HomeworkAdminResponse from(PersonalHomework personalHomework){
        Homework homework = personalHomework.getHomework();
        return HomeworkAdminResponse.builder()
                .id(homework.getId())
                .username(personalHomework.getStudent().getName())
                .title(homework.getTitle())
                .createdAt(homework.getCreatedAt())
                .status(personalHomework.getStatus().name())
                .build();
    }
}
