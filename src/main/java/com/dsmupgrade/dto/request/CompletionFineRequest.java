package com.dsmupgrade.dto.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompletionFineRequest {
    @NotNull
    private Integer fine_id;
}
