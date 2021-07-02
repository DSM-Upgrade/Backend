package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkContentResponse {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime deadline;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime returnAt;
    private String content;
    private String returnContent;
    private List<String> files;

    public static HomeworkContentResponse from(PersonalHomework personalHomework) {
        Homework homework = personalHomework.getHomework();
        List<String> files = personalHomework.getHomeworkFile().isEmpty() ?
            Collections.emptyList() : personalHomework.getHomeworkFile().stream().map(homeworkFile -> homeworkFile.getId().getName())
                    .collect(Collectors.toList());
        return HomeworkContentResponse.builder()
                .title(homework.getTitle())
                .createdAt(homework.getCreatedAt())
                .deadline(homework.getDeadline())
                .returnAt(personalHomework.getSubmittedAt())
                .content(homework.getContent())
                .returnContent(personalHomework.getContent())
                .files(files)
                .build();
    }
}
