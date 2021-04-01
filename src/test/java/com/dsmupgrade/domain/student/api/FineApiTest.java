package com.dsmupgrade.domain.student.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.fine.domain.Fine;
import com.dsmupgrade.domain.fine.domain.FineRepository;
import com.dsmupgrade.domain.fine.dto.request.CompletionFineRequest;
import com.dsmupgrade.domain.fine.dto.request.ImpositionRequest;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.dsmupgrade.domain.fine.dto.response.UserFineResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Calendar;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FineApiTest extends IntegrationTest {

    private static final String registeredUsername = "register123";

    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private ObjectMapper objectMapper;

    //@Before
    public int addFine(){
        Fine fine = Fine.builder()
                .amount(1000)
                .date(Calendar.getInstance().getTime())
                .reason("test")
                .username(registeredUsername)
                .isSubmitted(false)
                .build();
        fineRepository.save(fine);
        return fine.getId();
    }

    @After
    public void cleanup(){
        fineRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 모든_유저_벌금_리스트_반환() throws Exception {
        //given
        this.addFine();
        //when
        ResultActions resultActions = requestGetAllUserList();
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        List<AllUserFineResponse> responses = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<AllUserFineResponse>>() {});
        Assertions.assertEquals(responses.get(0).getFineReason(), "test");
        Assertions.assertEquals(responses.get(0).getFinePeopleName(), registeredUsername);
        Assertions.assertEquals(responses.get(0).getFineAmount(), 1000);
    }

    private ResultActions requestGetAllUserList() throws Exception {
        return requestMvc(get("/fine/list"));
    }

    @Test
    @WithMockUser(username = "register123")
    public void 유저_벌금_리스트_반환() throws Exception {
        //given
        this.addFine();
        //when
        ResultActions resultActions = requestGetUserList(registeredUsername);
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        List<UserFineResponse> responses = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<UserFineResponse>>() {});
        Assertions.assertEquals(responses.get(0).getFineReason(), "test");
        Assertions.assertEquals(responses.get(0).getFineAmount(), 1000);
    }

    private ResultActions requestGetUserList(String username) throws Exception {
        return requestMvc(get("/fine/list/" + username));
    }

    @Test
    @WithMockUser(username = "register123", roles = { "FINE_MANAGER" })
    public void 벌금_부과() throws Exception {
        //given
        ImpositionRequest impositionRequest = ImpositionRequest.builder()
                .userName(registeredUsername)
                .fineAmount(1000)
                .reason("test")
                .build();
        //when
        ResultActions resultActions = imposeFine(impositionRequest);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertEquals(fineRepository.findByUsername(registeredUsername).get(0).getReason(), "test");
        Assertions.assertEquals(fineRepository.findByUsername(registeredUsername).get(0).getAmount(), 1000);
    }

    private ResultActions imposeFine(ImpositionRequest dto) throws Exception {
        return requestMvc(post("/fine/imposition"), dto);
    }

    @Test
    @WithMockUser(username = "register123", roles = { "FINE_MANAGER" })
    public void 벌금_완료() throws Exception{
        //given
        int homeworkId = this.addFine();
        CompletionFineRequest completionFineRequest = new CompletionFineRequest();
        completionFineRequest.setFineId(homeworkId);
        //when
        ResultActions resultActions = completeFine(completionFineRequest);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertEquals(fineRepository.findById(homeworkId).get().getIsSubmitted(), true);
    }

    private ResultActions completeFine(CompletionFineRequest dto) throws Exception {
        return requestMvc(patch("/fine/completion"), dto);
    }

    @Test
    @WithMockUser(username = "register123", roles = { "FINE_MANAGER" })
    public void 벌금_삭제() throws Exception {
        //given
        int homeworkId = this.addFine();
        //when
        ResultActions resultActions = deleteFine(homeworkId);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertEquals(fineRepository.existsById(homeworkId), false);
    }

    private ResultActions deleteFine(int id) throws Exception {
        return requestMvc(delete("/fine/elimination/"+id));
    }
}
