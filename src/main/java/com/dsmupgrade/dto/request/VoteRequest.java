package com.dsmupgrade.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {
    private String title;

    private int count;

    private boolean isEven;

    private String[] content;

}
