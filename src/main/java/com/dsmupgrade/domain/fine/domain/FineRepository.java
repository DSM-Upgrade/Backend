package com.dsmupgrade.domain.fine.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Integer> {
    List<Fine> findAllByUsername(String username);
    Fine findAllById(Integer id);
    void deleteById(Integer id);
}
