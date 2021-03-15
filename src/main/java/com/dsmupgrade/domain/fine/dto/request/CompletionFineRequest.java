package com.dsmupgrade.domain.fine.dto.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompletionFineRequest {
    @NotNull
    private Integer fineId;
}
