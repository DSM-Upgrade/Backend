package com.dsmupgrade.domain.field.controller;

import com.dsmupgrade.domain.field.dto.response.FieldResponse;
import com.dsmupgrade.domain.field.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping("/fields")
    public List<FieldResponse> getAllFields() {
        return fieldService.getAllFields();
    }
}
