package com.dsmupgrade.domain.repository;

import com.dsmupgrade.domain.entity.Field;

import java.util.List;
import java.util.Optional;

public interface FieldRepository {

    Optional<Field> findById(Integer id);
    List<Field> findAll();
}
