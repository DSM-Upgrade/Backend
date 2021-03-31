package com.dsmupgrade.domain.student.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.fine.domain.Fine;
import com.dsmupgrade.domain.fine.domain.FineRepository;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Calendar;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class FineApiTest extends IntegrationTest {

    private static final String registeredUsername = "register123";

    @Autowired
    private FineRepository findRepository;

    @BeforeEach
    public void setup(){
        Fine fine = Fine.builder()
                .amount(1000)
                .date(Calendar.getInstance().getTime())
                .reason("test")
                .username(registeredUsername)
                .isSubmitted(false)
                .build();
        findRepository.save(fine);
    }

    @AfterEach
    public void cleanup(){
        findRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "register123", roles = { "ADMIN" })
    public void 모든_유저_리스트_반환() throws Exception {
        //given

        //when
        ResultActions resultActions = requestGetUserList();
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andReturn();

        List<AllUserFineResponse> responses = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<AllUserFineResponse>>(){});

        Assertions.assertEquals(responses.get(0).getFineReason(), "test");
        Assertions.assertEquals(responses.get(0).getFinePeopleName(), "register123");
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
