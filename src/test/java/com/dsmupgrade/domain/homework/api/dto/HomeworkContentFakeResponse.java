package com.dsmupgrade.domain.homework.api.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkContentFakeResponse {
    private String title;
    private String createdAt;
    private String deadline;
    private String returnAt;
    private String content;
    private String returnContent;
    private List<String> files;
}

