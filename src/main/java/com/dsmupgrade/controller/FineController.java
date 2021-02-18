package com.dsmupgrade.controller;

import com.dsmupgrade.domain.entity.Fine;
import com.dsmupgrade.domain.repository.FineRepository;
import com.dsmupgrade.dto.request.CompletionFine;
import com.dsmupgrade.dto.request.ImpositionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequestMapping("fine")
@RestController
public class FineController {
    @Autowired
    private FineRepository fineRepository;

    @GetMapping("list")
    public List<Fine> getAllUser_FineList(){ //모든 유저의 따른 벌금리스트를 받아옴 (아직 형식이 안 맞음)
        List<Fine> all_fineList =  fineRepository.findAll();
        return all_fineList;
    }
    @GetMapping("list/{username}")
    public List<Fine> getUser_FineList(@PathVariable("username") String username){ //유저의 따른 벌금리스트를 받아옴 (아직 형식이 안 맞음)
        List<Fine> user_fineList =  fineRepository.findAllByUsername(username);
        return user_fineList;
    }
    @PostMapping("imposition")
    public void imposeFine(@RequestBody @Valid ImpositionRequest impositionRequest){ // 유저에 따른 벌금 부과 (아직 토큰 확인 안함)
        Fine fine = new Fine();
        fine.setAmount(impositionRequest.getFine());
        Calendar time = Calendar.getInstance();
        fine.setDate(time.getTime());
        fine.setReason(impositionRequest.getReason());
        fine.setUsername(impositionRequest.getUserName());
        fine.setIs_submitted("no");
        fineRepository.save(fine);
    }
    @PatchMapping("completion")
    public void completeFine(@RequestBody @Valid CompletionFine completionFine){ // 유저가 벌금을 냄 (아직 토큰 확인 안함)
        Fine fine =  fineRepository.findAllById(completionFine.getFine_id());
        fine.setIs_submitted("yes");
        fineRepository.save(fine);
    }
    @DeleteMapping("elimination/{fineId}") // 유저에게 부과된 벌금을 없앰 (아직 토큰 확인 안함)
    public void eliminateFine(@PathVariable("fineId") Integer fineId){ // 유저에게 부과된 벌금을 없앰 (아직 null 처리 안함 + 토큰 확인 안함)
        fineRepository.deleteById(fineId);
    }
}
