package com.dsmupgrade.domain.student.controller;

import com.dsmupgrade.domain.student.dto.request.PasswordRequest;
import com.dsmupgrade.domain.student.dto.request.UpdateStudentRequest;
import com.dsmupgrade.domain.student.dto.response.StudentResponse;
import com.dsmupgrade.global.security.AuthenticationFacade;
import com.dsmupgrade.domain.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    public void updateStudentProfile(MultipartFile file) {

    }
}
