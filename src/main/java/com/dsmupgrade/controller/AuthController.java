package com.dsmupgrade.controller;

import com.dsmupgrade.dto.request.LoginRequest;
import com.dsmupgrade.dto.request.SignUpRequest;
import com.dsmupgrade.dto.response.LoginResponse;
import com.dsmupgrade.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
    }

    @PostMapping
    LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PatchMapping
    String generateNewToken(@RequestBody @NotBlank String refreshToken) {
        return authService.generateNewToken(refreshToken);
    }
}
