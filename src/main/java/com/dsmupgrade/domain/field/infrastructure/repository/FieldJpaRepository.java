package com.dsmupgrade.domain.field.infrastructure.repository;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.field.domain.FieldRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldJpaRepository extends FieldRepository, JpaRepository<Field, Integer> {

    @Override
    Optional<Field> findById(Integer id);

    @Override
    List<Field> findAll();
}
