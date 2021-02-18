package com.dsmupgrade.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImpositionRequest {
    private String userName;
    private Integer fine;
    private String reason;
}
