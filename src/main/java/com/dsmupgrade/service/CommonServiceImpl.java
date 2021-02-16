package com.dsmupgrade.service;

import com.dsmupgrade.domain.repository.FieldRepository;
import com.dsmupgrade.dto.response.FieldResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final FieldRepository fieldRepository;

    @Override
    public List<FieldResponse> getAllFields() {
        return fieldRepository.findAll()
                .stream().map(FieldResponse::of)
                .collect(Collectors.toList());
    }
}
