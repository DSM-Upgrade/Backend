package com.dsmupgrade.domain.entity.vote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteDoRepository extends JpaRepository<VoteDo,Integer> {
    List<VoteDo> findAllByUsernameAndVoteId(String username, Integer voteId);
    boolean existsByUsernameAndVoteId(String username, Integer voteId);

}
