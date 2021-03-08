package com.dsmupgrade.domain.vote.service;

import com.dsmupgrade.domain.vote.dto.request.VoteRequest;
import com.dsmupgrade.domain.vote.dto.response.VoteResponse;

public interface VoteService {
    void voteWrite(VoteRequest voteRequest);
    void voteDo(Integer[] choice,Integer id);
    VoteResponse getVote(Integer id);
    void voteUpdate(VoteRequest voteRequest,Integer id);
}
