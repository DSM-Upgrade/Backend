package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class UserHomeworkResponse {
    private String homeworkTitle;
    private LocalDateTime homeworkStart;
    private LocalDateTime homeworkEnd;
    private LocalDateTime homeworkSubmittedDate;
    private String homeworkContent;
    private String homeworkReturn;
    private List<String> homeworkFileName;
    private List<String> personalHomeworkFileName;

    public static UserHomeworkResponse from(PersonalHomework personalHomework){
        return UserHomeworkResponse.builder()
                .homeworkTitle(personalHomework.getHomework().getTitle())
                .homeworkStart(personalHomework.getHomework().getCreatedAt())
                .homeworkEnd(personalHomework.getHomework().getDeadline())
                .homeworkSubmittedDate(personalHomework.getSubmittedAt())
                .homeworkContent(personalHomework.getHomework().getContent())
                .homeworkReturn(personalHomework.getContent())
                .homeworkFileName(personalHomework.getHomework().getHomeworkFile().stream().map(
                        (file)-> { return file.getName(); }
                ).collect(Collectors.toList()))
                .homeworkFileName(personalHomework.getPersonalHomeworkFile().stream().map(
                        (file)-> { return file.getName(); }
                ).collect(Collectors.toList()))
                .build();
    }
}
