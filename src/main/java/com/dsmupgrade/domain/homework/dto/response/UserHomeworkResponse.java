package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.fine.domain.Fine;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.dsmupgrade.domain.homework.domain.HomeworkRepository;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class UserHomeworkResponse {
    private String homeworkTitle;
    private Date homeworkStart;
    private Date homeworkEnd;
    private Date homeworkSubmittedDate;
    private String homeworkContent;
    private String homeworkReturn;

    public static UserHomeworkResponse from(PersonalHomework personalHomework){
        return UserHomeworkResponse.builder()
                .homeworkTitle(personalHomework.getHomework().getTitle())
                .homeworkStart(personalHomework.getHomework().getCreatedAt())
                .homeworkEnd(personalHomework.getHomework().getDeadline())
                .homeworkSubmittedDate(personalHomework.getSubmittedAt())
                .homeworkContent(personalHomework.getHomework().getContent())
                .homeworkReturn(personalHomework.getContent())
                .build();
    }
}
