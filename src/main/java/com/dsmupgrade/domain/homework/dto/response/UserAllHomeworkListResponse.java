package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import com.dsmupgrade.domain.homework.domain.PersonalHomeworkStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserAllHomeworkListResponse {
    private Integer homeworkId;
    private String homeworkTitle;
    private Date homeworkStart;
    private Date homeworkEnd;
    private String homeworkStatus;

    public static UserAllHomeworkListResponse from(PersonalHomework personalHomework){
        return UserAllHomeworkListResponse.builder()
                .homeworkId(personalHomework.getHomeworkId())
                .homeworkTitle(personalHomework.getHomework().getTitle())
                .homeworkStart(personalHomework.getHomework().getCreatedAt())
                .homeworkEnd(personalHomework.getHomework().getDeadline())
                .homeworkStatus(personalHomework.getStatus().name())
                .build();
    }
}
