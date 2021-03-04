package com.dsmupgrade.controller;

import com.dsmupgrade.dto.request.VoteRequest;
import com.dsmupgrade.dto.response.VoteResponse;
import com.dsmupgrade.service.vote.VoteService;
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
