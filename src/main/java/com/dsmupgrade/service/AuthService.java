package com.dsmupgrade.service;

import com.dsmupgrade.dto.request.LoginRequest;
import com.dsmupgrade.dto.request.SignUpRequest;
import com.dsmupgrade.dto.response.LoginResponse;
import com.dsmupgrade.dto.response.TokenRefreshResponse;

public interface AuthService {

    void signUp(SignUpRequest signUpRequest);

    LoginResponse login(LoginRequest loginRequest);

    TokenRefreshResponse generateNewToken(String refreshToken);
}
