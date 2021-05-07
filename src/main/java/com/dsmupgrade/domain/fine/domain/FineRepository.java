package com.dsmupgrade.domain.fine.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
    List<Fine> findByUsername(String username);
    Optional<Fine> findById(Integer id);
    void deleteById(Integer id);
}
