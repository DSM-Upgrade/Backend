package com.dsmupgrade.domain.field.service;

import com.dsmupgrade.domain.field.dto.response.FieldResponse;

import java.util.List;

public interface FieldService {

    List<FieldResponse> getAllFields();
}
