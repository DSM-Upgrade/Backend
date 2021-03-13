package com.dsmupgrade.domain.student.controller;

import com.dsmupgrade.domain.student.dto.request.LoginRequest;
import com.dsmupgrade.domain.student.dto.request.SignUpRequest;
import com.dsmupgrade.domain.student.dto.response.LoginResponse;
import com.dsmupgrade.domain.student.dto.response.TokenRefreshResponse;
import com.dsmupgrade.domain.student.service.AuthService;
import com.dsmupgrade.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-up")
    void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
    }

    @PostMapping
    LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PatchMapping
    TokenRefreshResponse generateNewToken(HttpServletRequest request) {
        return authService.generateNewToken(jwtTokenProvider.resolveToken(request));
    }
}
