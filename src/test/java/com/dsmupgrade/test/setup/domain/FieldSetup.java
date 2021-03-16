package com.dsmupgrade.test.setup.domain;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.field.domain.FieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
@RequiredArgsConstructor
public class FieldSetup {

    private final FieldRepository fieldRepository;

    public Field save() {
        final Field field = buildField(1);
        return fieldRepository.save(field);
    }

    public Field build() {
        return buildField(1);
    }

    private Field buildField(int id) {
        return Field.builder()
                .id(id)
                .name("백엔드")
                .build();
    }
}
