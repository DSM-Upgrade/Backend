package com.dsmupgrade.domain.fine.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Integer> {
    List<Fine> findAllByUsername(String username);
    Optional<Fine> findAllById(Integer id);
    void deleteById(Integer id);
}
