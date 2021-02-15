package com.dsmupgrade.controller;

import com.dsmupgrade.dto.response.StudentResponse;
import com.dsmupgrade.global.security.AuthenticationFacade;
import com.dsmupgrade.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/")
    public StudentResponse getStudentInfoByUsername() {
        return studentService.getStudentByUsername(authenticationFacade.getUsername());
    }
}
