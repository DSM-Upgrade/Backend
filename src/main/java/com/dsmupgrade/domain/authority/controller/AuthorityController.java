package com.dsmupgrade.domain.authority.controller;



import com.dsmupgrade.domain.authority.dto.request.AdminAuthRequest;
import com.dsmupgrade.domain.authority.dto.response.ListStudentResponse;
import com.dsmupgrade.domain.authority.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService authorityService;
    @PostMapping("/homework")
    public void authorityHomework(@RequestBody AdminAuthRequest adminAuthRequest) {
        authorityService.authorityHomework(adminAuthRequest);
    }
    @PostMapping("/fine")
    public void authorityFine(@RequestBody AdminAuthRequest adminAuthRequest) {
        authorityService.authorityFine(adminAuthRequest);
    }
    @PostMapping("/auth")
    public void adminAuth(@RequestBody AdminAuthRequest adminAuthRequest) {
        authorityService.adminAuth(adminAuthRequest);
    }
    @GetMapping("/list/user")
    public List<ListStudentResponse> listUser(){
        return authorityService.listUser();
    }
    @GetMapping("/list/auth")
    public List<ListStudentResponse> listAuth(){
        return authorityService.listAuth();
    }
}
