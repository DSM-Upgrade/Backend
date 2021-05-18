package com.dsmupgrade.domain.fine.service;

import com.dsmupgrade.domain.fine.domain.Fine;
import com.dsmupgrade.domain.fine.domain.FineRepository;
import com.dsmupgrade.domain.fine.dto.request.CompletionFineRequest;
import com.dsmupgrade.domain.fine.dto.request.ImpositionRequest;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.dsmupgrade.domain.fine.dto.response.UserFineResponse;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.global.error.exception.FineNotFoundException;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<AllUserFineResponse> getAllUserFineList(){ //모든 유저의 따른 벌금리스트를 받아옴
        return fineRepository.findAll()
                .stream().map(AllUserFineResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserFineResponse> getUserFineList(String username){ //유저의 따른 벌금리스트를 받아옴
        return fineRepository.findByUsername(username)
                .stream().map(UserFineResponse::from)
                .collect(Collectors.toList());
    }
    @Override
    public void imposeFine(ImpositionRequest impositionRequest){ // 유저에 따른 벌금 부과
        studentRepository.findByUsername(impositionRequest.getUsername())
                .orElseThrow(() -> new StudentNotFoundException(impositionRequest.getUsername()));
        Fine fine = Fine.builder()
                .amount(impositionRequest.getFineAmount())
                .date(LocalDateTime.now())
                .reason(impositionRequest.getReason())
                .username(impositionRequest.getUsername())
                .isSubmitted(false)
                .build();
        fineRepository.save(fine);
    }
    @Override
    public void completeFine(CompletionFineRequest completionFineRequest){ // 유저가 벌금을 냄
        Fine fine = fineRepository.findById(completionFineRequest.getFineId()).
                orElseThrow(()->new FineNotFoundException(completionFineRequest.getFineId())); // Exception 만들어서 넣어야 함
        fine.setIsSubmitted(true);
        fineRepository.save(fine);
    }
    @Override
    public void eliminateFine(Integer fineId){ // 유저에게 부과된 벌금을 없앰 (아직 토큰 확인 안함)
        fineRepository.findById(fineId).orElseThrow(() -> new FineNotFoundException(fineId)); // Exception 만들어서 넣어야 함
        fineRepository.deleteById(fineId);
    }
}
