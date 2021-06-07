package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkListResponse {
    private int id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private String content;
    private String status;

    public static HomeworkListResponse from(PersonalHomework personalHomework){
        Homework homework = personalHomework.getHomework();
        return HomeworkListResponse.builder()
                .id(homework.getId())
                .title(homework.getTitle())
                .createdAt(homework.getCreatedAt())
                .deadline(homework.getDeadline())
                .content(homework.getContent())
                .status(personalHomework.getStatus().name())
                .build();
    }
}
