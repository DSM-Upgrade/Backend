package com.dsmupgrade.domain.homework.dto.request;

import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class ChangeHomeworkRequest {
    Integer homeworkId;
    private List<String> userName;
    private String homeworkTitle;
    private String homeworkContent;
    private Date deadline;
    // private MultipartFile File;
}
