package com.dsmupgrade.domain.field.service;

import com.dsmupgrade.domain.field.domain.FieldRepository;
import com.dsmupgrade.domain.field.dto.response.FieldResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;

    @Override
    public List<FieldResponse> getAllFields() {
        return fieldRepository.findAll()
                .stream().map(FieldResponse::from)
                .collect(Collectors.toList());
    }
}
