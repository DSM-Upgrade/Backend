package com.dsmupgrade.domain.fine.dto.request;

import com.sun.istack.NotNull;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImpositionRequest {
    @NotNull
    private String username;
    @NotNull
    private Integer fineAmount;
    @NotNull
    private String reason;
}
