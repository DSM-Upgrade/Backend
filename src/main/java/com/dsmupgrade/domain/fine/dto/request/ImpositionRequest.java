package com.dsmupgrade.domain.fine.dto.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImpositionRequest {
    @NotNull
    private String userName;
    @NotNull
    private Integer fineAmount;
    @NotNull
    private String reason;
}
