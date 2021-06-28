package com.dsmupgrade.domain.student.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentRequest {

    @Pattern(regexp = "^[1-3][1-4]([01][0-9]|2[0-1])$")
    private String studentNum;

    @Min(1)
    private Integer fieldId;
}
