package com.dsmupgrade.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAllHomeworkListResponse {
    private Integer homeworkId;
    private String homeworkTitle;
    private String homeworkStart;
    private String homeworkEnd;
    private Boolean homeworkSubmitted;
    private Boolean homeworkFinished;
}
