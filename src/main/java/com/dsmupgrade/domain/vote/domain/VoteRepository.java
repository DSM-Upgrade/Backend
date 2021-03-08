package com.dsmupgrade.domain.vote.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote,Integer> {
}
