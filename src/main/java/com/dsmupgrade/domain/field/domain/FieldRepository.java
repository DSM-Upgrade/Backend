package com.dsmupgrade.domain.field.domain;

import java.util.List;
import java.util.Optional;

public interface FieldRepository {

    Optional<Field> findById(Integer id);
    List<Field> findAll();
}
