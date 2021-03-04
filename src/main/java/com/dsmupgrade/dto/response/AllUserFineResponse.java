package com.dsmupgrade.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllUserFineResponse {
    private String finePeopleName;
    private Integer fineId;
    private String fineReason;
    private String fineDate;
    private Integer fineAmount;
    private Boolean isSubmitted;
}
