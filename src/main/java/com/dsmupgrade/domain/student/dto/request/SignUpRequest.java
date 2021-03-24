package com.dsmupgrade.domain.student.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @Pattern(regexp = "^[A-za-z0-9]{6,16}$")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$")
    private String password;

    @Pattern(regexp = "^[1-3][1-4]([01][0-9]|2[0-1])$")
    private String studentNum;

    @Pattern(regexp = "^[가-힣]{2,4}$")
    private String name;

    @NotNull
    private Integer fieldId;
}
