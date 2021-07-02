package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkAdminResponse {
    private int id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private String content;

    public static HomeworkAdminResponse from(Homework homework){
        return HomeworkAdminResponse.builder()
                .id(homework.getId())
                .title(homework.getTitle())
                .createdAt(homework.getCreatedAt())
                .deadline(homework.getDeadline())
                .content(homework.getContent())
                .build();
    }
}
