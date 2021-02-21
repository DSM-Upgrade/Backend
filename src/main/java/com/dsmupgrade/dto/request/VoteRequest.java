package com.dsmupgrade.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    private String title;

    private boolean isEven;

    private int count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadLine;

    private String[] content;

}
