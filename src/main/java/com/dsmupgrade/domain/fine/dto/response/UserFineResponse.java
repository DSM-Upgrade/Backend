package com.dsmupgrade.domain.fine.dto.response;

import com.dsmupgrade.domain.fine.domain.Fine;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserFineResponse {
    private final Integer fineId;
    private final String fineReason;
    private final Date fineDate;
    private final Integer fineAmount;
    private final Boolean isSubmitted;

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
