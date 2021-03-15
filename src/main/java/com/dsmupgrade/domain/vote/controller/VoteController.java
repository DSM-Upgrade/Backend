package com.dsmupgrade.domain.vote.controller;

import com.dsmupgrade.domain.vote.dto.request.VoteRequest;
import com.dsmupgrade.domain.vote.dto.response.VoteResponse;
import com.dsmupgrade.domain.vote.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    @PostMapping
    public void voteWrite(@RequestBody VoteRequest voteRequest) {
        voteService.voteWrite(voteRequest);
    }
    @GetMapping("{id}")
    public VoteResponse getVote(@PathVariable Integer id){
        return voteService.getVote(id);
    }
    @PostMapping("{id}")
    public void voteDo(@RequestBody Integer[] choice,@PathVariable Integer id){
        voteService.voteDo(choice,id);
    }
    @PatchMapping("{id}")
    public void voteUpdate(@RequestBody VoteRequest voteRequest,@PathVariable Integer id){
        voteService.voteUpdate(voteRequest,id);
    }


}
