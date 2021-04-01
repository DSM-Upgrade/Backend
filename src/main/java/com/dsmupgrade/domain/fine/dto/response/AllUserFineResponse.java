package com.dsmupgrade.domain.fine.dto.response;

import com.dsmupgrade.domain.fine.domain.Fine;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class AllUserFineResponse {
    private String finePeopleName;
    private Integer fineId;
    private String fineReason;
    private Date fineDate;
    private Integer fineAmount;
    private Boolean isSubmitted;

    public static AllUserFineResponse from(Fine fine){
        return AllUserFineResponse.builder()
                .finePeopleName(fine.getUsername())
                .fineId(fine.getId())
                .fineReason(fine.getReason())
                .fineDate(fine.getDate())
                .fineAmount(fine.getAmount())
                .isSubmitted(fine.getIsSubmitted())
                .build();
    }
}
