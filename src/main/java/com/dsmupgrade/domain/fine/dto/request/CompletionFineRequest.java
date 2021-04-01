package com.dsmupgrade.domain.fine.dto.request;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompletionFineRequest {
    @NotNull
    private Integer fineId;
}
