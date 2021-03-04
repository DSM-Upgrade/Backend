package com.dsmupgrade.dto.response;

import com.dsmupgrade.domain.entity.Field;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class FieldResponse {

    private final int id;

    private final String name;

    public static FieldResponse from(Field field) {
        return FieldResponse.builder()
                .id(field.getId())
                .name(field.getName())
                .build();
    }
}
