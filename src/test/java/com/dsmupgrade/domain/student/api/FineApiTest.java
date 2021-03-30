package com.dsmupgrade.domain.student.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.fine.domain.Fine;
import com.dsmupgrade.domain.fine.domain.FineRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @WithMockUser(username = "register123")
    public void 모든_유저_리스트_반환() throws Exception {
        //given
        //when
        ResultActions resultActions = requestGetUserList();
        //then
        resultActions
                .andExpect(status().isOk());
//                .andExpect(jsonPath("username").value(student.getUsername()))
//                .andExpect(jsonPath("student_num").value(student.getStudentNum()))
//                .andExpect(jsonPath("name").value(student.getName()))
//                .andExpect(jsonPath("field").value(student.getField().getName()));
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
