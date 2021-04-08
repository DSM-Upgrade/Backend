package com.dsmupgrade.domain.homework.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnHomeworkRequest {
    @NotNull
    private String userName;
    @NotNull
    private Integer homeworkId;
    @NotNull
    private String homeworkContent;
    // private MultipartFile File;
}