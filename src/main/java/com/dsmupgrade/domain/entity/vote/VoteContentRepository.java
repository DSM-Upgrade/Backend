package com.dsmupgrade.domain.entity.vote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteContentRepository extends JpaRepository<VoteContent,String> {
    List<VoteContent> findAllByVote(Vote vote);
}
