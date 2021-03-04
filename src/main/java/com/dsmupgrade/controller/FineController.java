package com.dsmupgrade.controller;

import com.dsmupgrade.domain.entity.Fine;
import com.dsmupgrade.domain.repository.FineRepository;
import com.dsmupgrade.dto.request.CompletionFineRequest;
import com.dsmupgrade.dto.request.ImpositionRequest;
import com.dsmupgrade.dto.response.AllUserFineResponse;
import com.dsmupgrade.dto.response.UserFineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RequestMapping("fine")
@RestController
public class FineController {
    @Autowired
    private FineRepository fineRepository;
    private SimpleDateFormat time_format;

    public FineController() {
        time_format = new SimpleDateFormat("yyyy-MM-dd");
    }

    @GetMapping("list")
    public List<AllUserFineResponse> getAllUserFineList(){ //모든 유저의 따른 벌금리스트를 받아옴
        List<Fine> allFineList =  fineRepository.findAll();
        List<AllUserFineResponse> allUserListResponse = new ArrayList<>();
        for(int i=0;i<allFineList.size();i++){ // 형식을 바꾸기 위해서
            AllUserFineResponse fine = new AllUserFineResponse();
            fine.setFinePeopleName(allFineList.get(i).getUsername());
            fine.setFineId(allFineList.get(i).getId());
            fine.setFineReason(allFineList.get(i).getReason());
            fine.setFineDate(time_format.format(allFineList.get(i).getDate()));
            fine.setFineAmount(allFineList.get(i).getAmount());
            fine.setIsSubmitted(allFineList.get(i).getIsSubmitted());
            allUserListResponse.add(fine);
        }
        return allUserListResponse;
    }
    @GetMapping("list/{username}")
    public List<UserFineResponse> getUserFineList(@PathVariable("username") String username){ //유저의 따른 벌금리스트를 받아옴
        List<Fine> userFineList =  fineRepository.findAllByUsername(username);
        List<UserFineResponse> UserListResponse = new ArrayList<>();
        for(int i=0;i<userFineList.size();i++){
            UserFineResponse fine = new UserFineResponse();
            fine.setFineId(userFineList.get(i).getId());
            fine.setFineReason(userFineList.get(i).getReason());
            fine.setFineDate(time_format.format(userFineList.get(i).getDate()));
            fine.setFineAmount(userFineList.get(i).getAmount());
            fine.setIsSubmitted(userFineList.get(i).getIsSubmitted());
            UserListResponse.add(fine);
        }
        return UserListResponse;
    }
    @PostMapping("imposition")
    public void imposeFine(@RequestBody @Valid ImpositionRequest impositionRequest){ // 유저에 따른 벌금 부과
        Fine fine = new Fine();
        fine.setAmount(impositionRequest.getFineAmount());
        Calendar time = Calendar.getInstance();
        fine.setDate(time.getTime());
        fine.setReason(impositionRequest.getReason());
        fine.setUsername(impositionRequest.getUserName());
        fine.setIsSubmitted(false);
        fineRepository.save(fine);
    }
    @PatchMapping("completion")
    public void completeFine(@RequestBody @Valid CompletionFineRequest completionFineRequest){ // 유저가 벌금을 냄
        Fine fine =  fineRepository.findAllById(completionFineRequest.getFineId());
        fine.setIsSubmitted(true);
        fineRepository.save(fine);
    }
    @DeleteMapping("elimination/{fineId}")
    public void eliminateFine(@PathVariable("fineId") Integer fineId){ // 유저에게 부과된 벌금을 없앰 (아직 토큰 확인 안함)
        fineRepository.deleteById(fineId);
    }
}
