package com.dsmupgrade.dto.request;

import com.dsmupgrade.global.error.exception.InvalidInputValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentRequest {

    @Pattern(regexp = "^[1-3][1-4][0-2][0-9]$")
    private String studentNum;

    @Min(1)
    private Integer fieldId;

    public void nullCheck() {
        if (studentNum == null && fieldId == null) {
            throw new InvalidInputValueException();
        }
    }
}
