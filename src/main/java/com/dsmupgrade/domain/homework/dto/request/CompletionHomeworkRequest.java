package com.dsmupgrade.domain.homework.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletionHomeworkRequest {
    private String userName;
    private Integer homeworkId;
}
