package com.dsmupgrade.domain.field.infrastructure.repository;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.field.domain.FieldRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldJpaRepository extends FieldRepository, JpaRepository<Field, Integer> {
}
