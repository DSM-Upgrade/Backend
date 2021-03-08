package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.domain.student.dto.request.LoginRequest;
import com.dsmupgrade.domain.student.dto.request.SignUpRequest;
import com.dsmupgrade.domain.student.dto.response.LoginResponse;
import com.dsmupgrade.domain.student.dto.response.TokenRefreshResponse;

public interface AuthService {

    void signUp(SignUpRequest signUpRequest);

    LoginResponse login(LoginRequest loginRequest);

    TokenRefreshResponse generateNewToken(String refreshToken);
}
