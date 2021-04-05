package com.dsmupgrade.domain.homework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentHomeworkRequest {
    private List<String> userName;
    private String homeworkTitle;
    private String homeworkContent;
    private Date deadline;
    // private MultipartFile File;
}
