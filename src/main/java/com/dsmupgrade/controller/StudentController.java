package com.dsmupgrade.controller;

import com.dsmupgrade.dto.request.PasswordRequest;
import com.dsmupgrade.dto.request.UpdateStudentRequest;
import com.dsmupgrade.dto.response.StudentResponse;
import com.dsmupgrade.global.security.AuthenticationFacade;
import com.dsmupgrade.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public StudentResponse getStudentInfoByUsername() {
        return studentService.getStudentByUsername(authenticationFacade.getUsername());
    }

    @PatchMapping("/password")
    public void updatePassword(@RequestBody @Valid PasswordRequest passwordRequest) {
        studentService.updateStudentPassword(authenticationFacade.getUsername(), passwordRequest);
    }

    @PatchMapping
    public void updateStudentInfo(@RequestBody @Valid UpdateStudentRequest updateStudentRequest) {
        studentService.updateStudentInfo(authenticationFacade.getUsername(), updateStudentRequest);
    }
}
