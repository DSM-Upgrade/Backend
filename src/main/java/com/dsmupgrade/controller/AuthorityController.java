package com.dsmupgrade.controller;



import com.dsmupgrade.dto.request.AdminAuthRequest;
import com.dsmupgrade.dto.response.ListStudentResponse;
import com.dsmupgrade.service.authority.AuthorityService;
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
