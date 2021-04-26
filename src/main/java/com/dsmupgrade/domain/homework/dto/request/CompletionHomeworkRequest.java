package com.dsmupgrade.domain.homework.dto.request;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletionHomeworkRequest {
    @NotNull
    private String username;
    @NotNull
    private Integer homeworkId;
}
