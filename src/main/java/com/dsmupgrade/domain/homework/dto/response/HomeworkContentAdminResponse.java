package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.HomeworkFile;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkContentAdminResponse {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime returnAt;
    private String content;

    public static HomeworkContentAdminResponse from(PersonalHomework personalHomework) {
        Homework homework = personalHomework.getHomework();
        return HomeworkContentAdminResponse.builder()
                .title(homework.getTitle())
                .createdAt(homework.getCreatedAt())
                .deadline(homework.getDeadline())
                .returnAt(personalHomework.getSubmittedAt())
                .content(personalHomework.getContent())
                .build();
    }
}
