package com.dsmupgrade.controller;

import com.dsmupgrade.dto.response.FieldResponse;
import com.dsmupgrade.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class CommonController {

    private final CommonService commonService;

    @GetMapping("/fields")
    public List<FieldResponse> getAllFields() {
        return commonService.getAllFields();
    }
}
