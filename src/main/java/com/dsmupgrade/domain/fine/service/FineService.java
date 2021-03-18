package com.dsmupgrade.domain.fine.service;

import com.dsmupgrade.domain.fine.dto.request.CompletionFineRequest;
import com.dsmupgrade.domain.fine.dto.request.ImpositionRequest;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.dsmupgrade.domain.fine.dto.response.UserFineResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Service
public interface FineService {
    List<AllUserFineResponse> getAllUserFineList();
    List<UserFineResponse> getUserFineList(String username);
    void imposeFine(ImpositionRequest impositionRequest);
    void completeFine(CompletionFineRequest completionFineRequest);
    void eliminateFine(Integer fineId);
}
