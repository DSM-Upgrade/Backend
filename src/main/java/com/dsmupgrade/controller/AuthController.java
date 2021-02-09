package com.dsmupgrade.controller;

import com.dsmupgrade.dto.request.SignUpRequest;
import com.dsmupgrade.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final StudentService studentService;

    @PostMapping("/sign-up")
    void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        studentService.signUp(signUpRequest);
    }
}
