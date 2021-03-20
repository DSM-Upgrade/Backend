package com.dsmupgrade.domain.fine.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFineResponse {
    private Integer fineId;
    private String fineReason;
    private String fineDate;
    private Integer fineAmount;
    private Boolean isSubmitted;
}
