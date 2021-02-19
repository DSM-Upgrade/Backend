package com.dsmupgrade.controller;

import com.dsmupgrade.domain.entity.Fine;
import com.dsmupgrade.domain.repository.FineRepository;
import com.dsmupgrade.dto.request.CompletionFineRequest;
import com.dsmupgrade.dto.request.ImpositionRequest;
import com.dsmupgrade.dto.response.AllUserFineResponse;
import com.dsmupgrade.dto.response.UserFineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

@RequestMapping("fine")
@RestController
public class FineController {
    @Autowired
    private FineRepository fineRepository;

    @GetMapping("list")
    public List<AllUserFineResponse> getAllUser_FineList(){ //모든 유저의 따른 벌금리스트를 받아옴
        List<Fine> all_fineList =  fineRepository.findAll();
        List<AllUserFineResponse> allUserListResponse = new ArrayList<>();
        for(int i=0;i<all_fineList.size();i++){ // 형식을 바꾸기 위해서
            AllUserFineResponse fine = new AllUserFineResponse();
            fine.setFine_peopleName(all_fineList.get(i).getUsername());
            fine.setFine_id(all_fineList.get(i).getId());
            fine.setFine_reason(all_fineList.get(i).getReason());
            fine.setFine_date(all_fineList.get(i).getDate()); // date 형식 바꿔야 함
            fine.setFine(all_fineList.get(i).getAmount());
            fine.setIs_submitted(all_fineList.get(i).getIs_submitted());
            allUserListResponse.add(fine);
        }
        return allUserListResponse;
    }
    @GetMapping("list/{username}")
    public List<UserFineResponse> getUser_FineList(@PathVariable("username") String username){ //유저의 따른 벌금리스트를 받아옴
        List<Fine> user_fineList =  fineRepository.findAllByUsername(username);
        List<UserFineResponse> UserListResponse = new ArrayList<>();
        for(int i=0;i<user_fineList.size();i++){ // 형식을 바꾸기 위해서
            UserFineResponse fine = new UserFineResponse();
            fine.setFine_id(user_fineList.get(i).getId());
            fine.setFine_reason(user_fineList.get(i).getReason());
            fine.setFine_date(user_fineList.get(i).getDate()); // date 형식 바꿔야 함
            fine.setFine(user_fineList.get(i).getAmount());
            fine.setIs_submitted(user_fineList.get(i).getIs_submitted());
            UserListResponse.add(fine); // 여기가 문제임
        }
        return UserListResponse;
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
    public void completeFine(@RequestBody @Valid CompletionFineRequest completionFineRequest){ // 유저가 벌금을 냄 (아직 토큰 확인 안함)
        Fine fine =  fineRepository.findAllById(completionFineRequest.getFine_id());
        fine.setIs_submitted("yes");
        fineRepository.save(fine);
    }
    @DeleteMapping("elimination/{fineId}")
    public void eliminateFine(@PathVariable("fineId") Integer fineId){ // 유저에게 부과된 벌금을 없앰 (아직 토큰 확인 안함)
        fineRepository.deleteById(fineId);
    }
}
