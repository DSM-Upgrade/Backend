package com.dsmupgrade.domain.vote.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteDoRepository extends JpaRepository<VoteDo,Integer> {
    List<VoteDo> findAllByUsernameAndVoteId(String username, Integer voteId);
    boolean existsByUsernameAndVoteId(String username, Integer voteId);

}
