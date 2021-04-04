package com.dsmupgrade.domain.homework.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.fine.dto.response.AllUserFineResponse;
import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.response.UserAllHomeworkListResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.engine.TestExecutionResult;
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

public class HomeworkApiTest extends IntegrationTest {

    private static final String registeredUsername = "register123";

    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private PersonalHomeworkRepository personalHomeworkRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private int addHomework(){
        Homework homework = Homework.builder()
                .title("test")
                .content("test")
                .createdAt(Calendar.getInstance().getTime())
                .deadline(Calendar.getInstance().getTime())
                .build();
        homeworkRepository.save(homework);
        List<String> usernameList = new ArrayList<String>();
        usernameList.add(registeredUsername);
        for (String user : usernameList){
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .studentUsername(user)
                    .status(PersonalHomeworkStatus.ASSIGNED)
                    .submittedAt(null)
                    .content(null)
                    .homework(homework)
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }
        return homework.getId();
    }

    @After
    public void clenaup(){
        homeworkRepository.deleteAll();
        personalHomeworkRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 유저_할당된_숙제리스트() throws Exception {
        //given
        int homeworkId = this.addHomework();
        //when
        ResultActions resultActions = requestGetUserHomeworkList(registeredUsername);
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        UserAllHomeworkListResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<UserAllHomeworkListResponse>>() {}).get(0);
        System.out.println(response.getHomeworkId().getClass().getName());

        Assertions.assertEquals(response.getHomeworkId(), homeworkId);
        Assertions.assertEquals(response.getHomeworkStatus(), "ASSIGNED");
        Assertions.assertEquals(response.getHomeworkTitle(), "test");
    }

    private ResultActions requestGetUserHomeworkList(String username) throws Exception {
        return requestMvc(get("/homework/list/" + username));
    }
}
