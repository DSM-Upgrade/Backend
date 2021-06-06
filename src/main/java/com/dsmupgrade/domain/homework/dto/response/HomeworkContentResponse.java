package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.HomeworkFile;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkContentResponse {
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private LocalDateTime returnAt;
    private String content;
    private String returnContent;
    private List<String> files;

    public static HomeworkContentResponse from(PersonalHomework personalHomework) {
        Homework homework = personalHomework.getHomework();
        return HomeworkContentResponse.builder()
                .title(homework.getTitle())
                .createdAt(homework.getCreatedAt())
                .deadline(homework.getDeadline())
                .returnAt(personalHomework.getSubmittedAt())
                .content(homework.getContent())
                .returnContent(personalHomework.getContent())
                .files(personalHomework.getHomeworkFile()
                        .stream().map(HomeworkFile::getName)
                        .collect(Collectors.toList()))
                .build();
    }
}
