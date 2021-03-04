package com.dsmupgrade.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserFineResponse {
    private Integer fineId;
    private String fineReason;
    private String fineDate;
    private Integer fineAmount;
    private Boolean isSubmitted;
}
