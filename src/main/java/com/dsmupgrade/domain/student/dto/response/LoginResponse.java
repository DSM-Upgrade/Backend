package com.dsmupgrade.domain.student.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {

    private final String accessToken;

    private final String refreshToken;
}
