package com.dsmupgrade.domain.homework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnHomeworkRequest {
    private String userName;
    private Integer homeworkId;
    private String homeworkContent;
    // private MultipartFile File;
}