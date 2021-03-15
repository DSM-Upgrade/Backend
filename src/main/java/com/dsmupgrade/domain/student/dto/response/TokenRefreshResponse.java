package com.dsmupgrade.domain.student.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenRefreshResponse {

    private final String accessToken;
}
