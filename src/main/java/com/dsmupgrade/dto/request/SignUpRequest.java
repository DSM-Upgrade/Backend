package com.dsmupgrade.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Pattern(regexp = "^[A-za-z0-9]{6,16}/g")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$")
    private String password;

    @Min(1) @Max(3)
    private Integer grade;

    @Min(1) @Max(4)
    private Integer cls;

    @Min(1) @Max(21)
    private Integer number;

    @Pattern(regexp = "^[가-힣]{2,4}$")
    private String name;

    @NotNull
    private Integer fieldId;
}
