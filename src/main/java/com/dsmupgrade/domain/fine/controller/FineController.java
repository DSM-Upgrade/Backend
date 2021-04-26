package com.dsmupgrade.domain.fine.controller;

import com.dsmupgrade.domain.fine.dto.request.CompletionFineRequest;
import com.dsmupgrade.domain.fine.dto.request.ImpositionRequest;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.dsmupgrade.domain.fine.dto.response.UserFineResponse;
import com.dsmupgrade.domain.fine.service.FineService;
import com.dsmupgrade.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/fine")
@RestController
@RequiredArgsConstructor
public class FineController {
    private final FineService fineService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/list/all")
    public List<AllUserFineResponse> getAllUserFineList(){ //모든 유저의 따른 벌금리스트를 받아옴
        return fineService.getAllUserFineList();
    }

    @GetMapping("/list")
    public List<UserFineResponse> getUserFineList(){ //유저의 따른 벌금리스트를 받아옴
        return fineService.getUserFineList(authenticationFacade.getUsername());
    }

    @PostMapping("/imposition")
    public void imposeFine(@RequestBody @Valid ImpositionRequest impositionRequest){ // 유저에 따른 벌금 부과
        fineService.imposeFine(impositionRequest);
    }
    @PatchMapping("/completion")
    public void completeFine(@RequestBody @Valid CompletionFineRequest completionFineRequest){ // 유저가 벌금을 냄
        fineService.completeFine(completionFineRequest);
    }
    @DeleteMapping("/elimination/{fineId}")
    public void eliminateFine(@PathVariable("fineId") Integer fineId){ // 유저에게 부과된 벌금을 없앰 (아직 토큰 확인 안함)
        fineService.eliminateFine(fineId);
    }
}
