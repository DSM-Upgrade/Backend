package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import com.dsmupgrade.domain.homework.domain.PersonalHomeworkStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class UserAllHomeworkListResponse {
    private Integer homeworkId;
    private String homeworkTitle;
    private LocalDateTime homeworkStart;
    private LocalDateTime homeworkEnd;
    private String homeworkStatus;

    public static UserAllHomeworkListResponse from(PersonalHomework personalHomework){
        return UserAllHomeworkListResponse.builder()
                .homeworkId(personalHomework.getHomework().getId())
                .homeworkTitle(personalHomework.getHomework().getTitle())
                .homeworkStart(personalHomework.getHomework().getCreatedAt())
                .homeworkEnd(personalHomework.getHomework().getDeadline())
                .homeworkStatus(personalHomework.getStatus().name())
                .build();
    }
}
