package com.dsmupgrade.domain.homework.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserHomeworkResponse {
    private String homeworkTitle;
    private String homeworkStart;
    private String homeworkEnd;
    private String homeworkSubmittedDate;
    private String homeworkContent;
    private String homeworkReturn;
}
