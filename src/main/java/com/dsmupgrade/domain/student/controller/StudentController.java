package com.dsmupgrade.domain.student.controller;

import com.dsmupgrade.domain.student.dto.request.PasswordRequest;
import com.dsmupgrade.domain.student.dto.request.UpdateStudentRequest;
import com.dsmupgrade.domain.student.dto.response.StudentResponse;
import com.dsmupgrade.domain.student.service.StudentFindService;
import com.dsmupgrade.domain.student.service.StudentUpdateService;
import com.dsmupgrade.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentFindService studentFindService;
    private final StudentUpdateService studentUpdateService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public StudentResponse getStudentByUsername() {
        return studentFindService.getStudentByUsername(getUsername());
    }

    @PatchMapping("/password")
    public void updatePassword(@RequestBody @Valid PasswordRequest passwordRequest) {
        studentUpdateService.updateStudentPassword(getUsername(), passwordRequest);
    }

    @PatchMapping
    public void updateStudentInfo(@RequestBody @Valid UpdateStudentRequest updateStudentRequest) {
        studentUpdateService.updateStudentInfo(getUsername(), updateStudentRequest);
    }

    @PatchMapping("/profile")
    public void updateStudentProfile(@RequestPart MultipartFile file) {
        studentUpdateService.updateStudentProfile(getUsername(), file);
    }

    private String getUsername() {
        return authenticationFacade.getUsername();
    }
}
