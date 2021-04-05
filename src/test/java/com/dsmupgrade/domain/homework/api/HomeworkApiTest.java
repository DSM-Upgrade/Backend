package com.dsmupgrade.domain.homework.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.AssignmentHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.CompletionHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ReturnHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.response.UserAllHomeworkListResponse;
import com.dsmupgrade.domain.homework.dto.response.UserHomeworkResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Homework addHomework(){
        Homework homework = Homework.builder()
                .title("test")
                .content("test")
                .createdAt(Calendar.getInstance().getTime())
                .deadline(Calendar.getInstance().getTime())
                .build();
        homeworkRepository.save(homework);
        return homework;
    }
    private void addPersonalHomework(Homework homework){
        List<String> usernameList = new ArrayList<String>();
        usernameList.add(registeredUsername);
        for (String user : usernameList){
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .studentHomeworkId(homework+registeredUsername)
                    .studentUsername(user)
                    .status(PersonalHomeworkStatus.ASSIGNED)
                    .submittedAt(null)
                    .content(null)
                    .homework(homework)
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }
    }

    @After
    public void clenaup(){
        personalHomeworkRepository.deleteAll();
        homeworkRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 유저_할당된_숙제리스트() throws Exception {
        //given
        Homework homework = this.addHomework();
        this.addPersonalHomework(homework);
        //when
        ResultActions resultActions = requestGetUserHomeworkList(registeredUsername);
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        UserAllHomeworkListResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<UserAllHomeworkListResponse>>() {}).get(0);

        Assertions.assertEquals(response.getHomeworkId(), homework.getId());
        Assertions.assertEquals(response.getHomeworkStatus(), "ASSIGNED");
        Assertions.assertEquals(response.getHomeworkTitle(), "test");
    }

    private ResultActions requestGetUserHomeworkList(String username) throws Exception {
        return requestMvc(get("/homework/list/" + username));
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 유저_할당된_숙제내용() throws Exception {
        //given
        Homework homework = this.addHomework();
        this.addPersonalHomework(homework);
        //when
        ResultActions resultActions = requestGetUserHomeworkContent(registeredUsername, homework.getId());
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        UserHomeworkResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserHomeworkResponse.class);
        Assertions.assertEquals(response.getHomeworkTitle(),"test");
        Assertions.assertEquals(response.getHomeworkContent(),"test");
        Assertions.assertEquals(response.getHomeworkReturn(),null);
    }

    private ResultActions requestGetUserHomeworkContent(String username, int homeworkId) throws Exception {
        return requestMvc(get("/homework/content/" + username +"/"+ homeworkId));
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 숙제할당() throws Exception{
        //given
        List<String> userName = new ArrayList<>();
        userName.add(registeredUsername);
        AssignmentHomeworkRequest assignmentHomeworkRequest = AssignmentHomeworkRequest.builder()
                .userName(userName)
                .homeworkTitle("test")
                .homeworkContent("test")
                .deadline(Calendar.getInstance().getTime())
                .build();
        //when
        ResultActions resultActions = assignmentHomework(assignmentHomeworkRequest);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertEquals(personalHomeworkRepository.findByStudentUsername(registeredUsername).isEmpty(), false);
    }

    private ResultActions assignmentHomework(AssignmentHomeworkRequest dto) throws Exception {
        return requestMvc(post("/homework/assignment"), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 숙제반환() throws Exception{
        //given
        Homework homework = this.addHomework();
        this.addPersonalHomework(homework);
        int homeworkId = homework.getId();
        ReturnHomeworkRequest returnHomeworkRequest = ReturnHomeworkRequest.builder()
                .userName(registeredUsername)
                .homeworkId(homeworkId)
                .homeworkContent("test")
                .build();
        //when
        ResultActions resultActions = returnHomework(returnHomeworkRequest);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        homework = homeworkRepository.findById(homeworkId).get();
        PersonalHomework personalHomework = personalHomeworkRepository.findByStudentUsernameAndHomework(registeredUsername, homework).get();
        Assertions.assertEquals(personalHomework.getHomework().getId(), homeworkId);
        Assertions.assertEquals(personalHomework.getStatus(), PersonalHomeworkStatus.SUBMITTED);
        Assertions.assertEquals(personalHomework.getContent(), "test");
        Assertions.assertEquals(personalHomework.getSubmittedAt()==null, false);
    }

    private ResultActions returnHomework(ReturnHomeworkRequest dto) throws Exception {
        return requestMvc(post("/homework/return"), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 숙제완료() throws Exception{
        //given
        Homework homework = this.addHomework();
        this.addPersonalHomework(homework);
        CompletionHomeworkRequest completionHomeworkRequest = CompletionHomeworkRequest.builder()
                .userName(registeredUsername)
                .homeworkId(homework.getId())
                .build();
        //when
        ResultActions resultActions = comlpleteHomework(completionHomeworkRequest);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertEquals(personalHomeworkRepository.findByStudentUsernameAndHomework(registeredUsername,homework).get().getStatus(), PersonalHomeworkStatus.FINISHED);
    }

    private ResultActions comlpleteHomework(CompletionHomeworkRequest dto) throws Exception {
        return requestMvc(post("/homework/completion"), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 숙제변경() throws Exception{

    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 숙제삭제() throws Exception{

    }
}
