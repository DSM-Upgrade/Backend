package com.dsmupgrade.domain.vote.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteContentRepository extends JpaRepository<VoteContent,String> {
    List<VoteContent> findAllByVote(Vote vote);
    void deleteAllByVote(Vote vote);
}
