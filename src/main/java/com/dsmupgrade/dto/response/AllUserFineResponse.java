package com.dsmupgrade.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AllUserFineResponse {
    private String fine_peopleName;
    private Integer fine_id;
    private String fine_reason;
    private String fine_date;
    private Integer fine;
    private Boolean is_submitted;
}
