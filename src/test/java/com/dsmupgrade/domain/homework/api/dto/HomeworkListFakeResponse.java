package com.dsmupgrade.domain.homework.api.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkListFakeResponse {
    private int id;
    private String title;
    private String createdAt;
    private String deadline;
    private String content;
    private String status;
}
