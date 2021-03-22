package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.domain.student.dto.response.TokenRefreshResponse;
import com.dsmupgrade.global.security.JwtTokenProvider;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class AuthServiceTest extends StudentServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    public void 토큰_재발급() {
        //given
        String refreshToken = "refresh_token";

        given(jwtTokenProvider.getUsername(refreshToken)).willReturn(student.getUsername());
        given(jwtTokenProvider.generateAccessToken(student.getUsername())).willReturn("access_token");

        //when
        TokenRefreshResponse response = authService.generateNewToken(refreshToken);

        //then
        assertThat(response.getAccessToken()).isEqualTo("access_token");
    }
}
