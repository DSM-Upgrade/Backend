package com.dsmupgrade.domain.fine.dto.response;

import com.dsmupgrade.domain.fine.domain.Fine;
import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class UserFineResponse {
    private Integer fineId;
    private String fineReason;
    private Date fineDate;
    private Integer fineAmount;
    private Boolean isSubmitted;

    public static UserFineResponse from(Fine fine){
        return UserFineResponse.builder()
                .fineId(fine.getId())
                .fineReason(fine.getReason())
                .fineDate(fine.getDate())
                .fineAmount(fine.getAmount())
                .isSubmitted(fine.getIsSubmitted())
                .build();
    }
}
