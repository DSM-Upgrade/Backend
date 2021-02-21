package com.dsmupgrade.controller;

import com.dsmupgrade.dto.request.AdminAuthRequest;
import com.dsmupgrade.service.authority.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authority")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService authorityService;
    @PostMapping("/homework")
    public void AuthorityHomework(@RequestBody AdminAuthRequest adminAuthRequest) {
        authorityService.AuthorityHomework(adminAuthRequest);
    }
    @PostMapping("/fine")
    public void AuthorityFine(@RequestBody AdminAuthRequest adminAuthRequest) {
        authorityService.AuthorityFine(adminAuthRequest);
    }
    @PostMapping("/auth")
    public void AdminAuth(@RequestBody AdminAuthRequest adminAuthRequest) {
        authorityService.AdminAuth(adminAuthRequest);
    }

}
