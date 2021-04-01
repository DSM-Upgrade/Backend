package com.dsmupgrade.domain.student.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.fine.domain.Fine;
import com.dsmupgrade.domain.fine.domain.FineRepository;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FineApiTest extends IntegrationTest {

    private static final String registeredUsername = "register123";

    @Autowired
    private FineRepository fineRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup(){
        Fine fine = Fine.builder()
                .amount(1000)
                .date(Calendar.getInstance().getTime())
                .reason("test")
                .username(registeredUsername)
                .isSubmitted(false)
                .build();
        fineRepository.save(fine);
    }

    @After
    public void cleanup(){
        fineRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 모든_유저_리스트_반환() throws Exception {
        //when
        ResultActions resultActions = requestGetUserList();
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        ArrayList<AllUserFineResponse> responses = objectMapper.readValue( // 안됨
                result.getResponse().getContentAsString(), new TypeReference<ArrayList<AllUserFineResponse>>() {});
        Assertions.assertEquals(responses.get(0).getFineReason(), "test");
        Assertions.assertEquals(responses.get(0).getFinePeopleName(), registeredUsername);
    }

    private ResultActions requestGetUserList() throws Exception {
        return requestMvc(get("/fine/list"));
    }

    @Test
    @WithMockUser(username = "register123")
    public void 유저_벌금_리스트_반환(){

    }

    @Test
    @WithMockUser(username = "register123", roles = { "FINE_MANAGER" })
    public void 벌금_부과(){

    }

    @Test
    @WithMockUser(username = "register123", roles = { "FINE_MANAGER" })
    public void 벌금_완료(){

    }

    @Test
    @WithMockUser(username = "register123", roles = { "FINE_MANAGER" })
    public void 벌금_삭제(){

    }
}
