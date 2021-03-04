package com.dsmupgrade.controller;

import com.dsmupgrade.dto.request.LoginRequest;
import com.dsmupgrade.dto.request.SignUpRequest;
import com.dsmupgrade.dto.response.LoginResponse;
import com.dsmupgrade.dto.response.TokenRefreshResponse;
import com.dsmupgrade.global.security.JwtTokenProvider;
import com.dsmupgrade.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

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
