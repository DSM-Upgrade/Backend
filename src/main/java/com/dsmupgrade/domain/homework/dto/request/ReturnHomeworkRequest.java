package com.dsmupgrade.domain.homework.dto.request;

import lombok.Getter;

@Getter
public class ReturnHomeworkRequest {
    private String userName;
    private Integer homeworkId;
    private String homeworkTitle;
    private String homeworkContent;
    // private MultipartFile File;
}