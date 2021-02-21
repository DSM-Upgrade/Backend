package com.dsmupgrade.service.vote;

import com.dsmupgrade.dto.request.VoteRequest;
import com.dsmupgrade.dto.response.VoteResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface VoteService {
    void voteWrite(VoteRequest voteRequest);
    void voteDo(Integer[] choice,Integer id);
    VoteResponse getVote(Integer id);
    void voteUpdate(VoteRequest voteRequest,Integer id);
}
