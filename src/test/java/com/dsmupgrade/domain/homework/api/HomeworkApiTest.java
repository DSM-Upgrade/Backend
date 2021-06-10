package com.dsmupgrade.domain.homework.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.HomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.PersonalHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.UserRequest;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkListResponse;
import com.dsmupgrade.global.error.exception.HomeworkNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.engine.User;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HomeworkApiTest extends IntegrationTest {

    @Autowired
    private HomeworkRepository homeworkRepository;
    @Autowired
    private PersonalHomeworkRepository personalHomeworkRepository;
    @Autowired
    private HomeworkFileRepository homeworkFileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String registeredUsername = "register123";

    @After
    public void clenaup(){
        homeworkFileRepository.deleteAll();
        personalHomeworkRepository.deleteAll();
        homeworkRepository.deleteAll();
    }

    private Homework addHomework(LocalDateTime deadline){
        Homework homework = Homework.builder()
                .title("test")
                .content("test")
                .createdAt(LocalDateTime.now())
                .deadline(deadline)
                .build();
        homeworkRepository.save(homework);
        return homework;
    }

    private void addPersonalHomework(Homework homework){
        List<String> usernameList = new ArrayList<String>();
        usernameList.add(registeredUsername);
        for (String user : usernameList){
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .id(new PersonalHomeworkPk(homework.getId(), user))
                    .status(PersonalHomeworkStatus.ASSIGNED)
                    .submittedAt(null)
                    .content("test")
                    .homework(homework)
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }
        homework.setPersonalHomeworks(personalHomeworkRepository.findByIdHomeworkId(homework.getId()));
    }

    private List<Homework> makeHomeworkList(int amount){
        List<Homework> homework = new ArrayList<>();
        for(int i=0; i<amount; i++){
            homework.add(this.addHomework(LocalDateTime.of(LocalDateTime.now().getYear()+4-((int)((Math.random()*10000)%10)), Month.JANUARY, 1, 10, 10, 30)));
            this.addPersonalHomework(homework.get(i));
        }
        return homework;
    }


    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_리스트_받아오기_성공() throws Exception {
        //given
        List<Homework> homeworks = makeHomeworkList(10);
        //when
        ResultActions resultActions = requestGetHomeworkList();
        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<HomeworkListResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<HomeworkListResponse>>() {});
        for (int i=0; i<response.size(); i++) {
            Assertions.assertEquals(response.get(i).getId(), homeworks.get(i).getId());
            Assertions.assertEquals(response.get(i).getCreatedAt(), homeworks.get(i).getCreatedAt());
            Assertions.assertEquals(response.get(i).getDeadline(), homeworks.get(i).getDeadline());
            Assertions.assertEquals(response.get(i).getTitle(), homeworks.get(i).getTitle());
            Assertions.assertEquals(response.get(i).getContent(), homeworks.get(i).getContent());
            if(response.get(i).getDeadline().isBefore(LocalDateTime.now()))
                Assertions.assertEquals(response.get(i).getStatus(), "UNSUBMITTED");
            else Assertions.assertEquals(response.get(i).getStatus(), "ASSIGNED");
        }
    }

    private ResultActions requestGetHomeworkList() throws Exception {
        return requestMvc(get("/homework"));
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_내용_받아오기_성공() throws Exception {
        //given
        Homework homework = makeHomeworkList(1).get(0);
        //when
        ResultActions resultActions = requestGetHomeworkContent(homework.getId());
        //then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        HomeworkContentResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<Optional<HomeworkContentResponse>>() {})
                .orElseThrow(()->new HomeworkNotFoundException(homework.getId()));

        PersonalHomework personalHomework = personalHomeworkRepository
                .findById(new PersonalHomeworkPk(homework.getId(), registeredUsername))
                .orElseThrow(()->new HomeworkNotFoundException(homework.getId()));

        Assertions.assertEquals(response.getTitle(), homework.getTitle());
        Assertions.assertEquals(response.getCreatedAt(), homework.getCreatedAt());
        Assertions.assertEquals(response.getDeadline(), homework.getDeadline());
        Assertions.assertEquals(response.getReturnAt(), personalHomework.getSubmittedAt());
        Assertions.assertEquals(response.getContent(), homework.getContent());
        Assertions.assertEquals(response.getReturnContent(), personalHomework.getContent());
        Assertions.assertNull(response.getFiles());
    }

    private ResultActions requestGetHomeworkContent(int id) throws Exception {
        return requestMvc(get("/homework/" + id));
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_할당_성공() throws Exception {
        //given
        List<String> users = new ArrayList<String>();
        users.add(registeredUsername);
        HomeworkRequest homeworkRequest = HomeworkRequest.builder()
                .title("TestTitle")
                .content("TestContent")
                .deadline(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY, LocalDateTime.now().getDayOfMonth()+1, 10, 10, 30))
                .usernames(users)
                .build();
        //when
        ResultActions resultActions = requestAssignmentHomework(homeworkRequest);
        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print());

        Assertions.assertFalse(homeworkRepository.findAll().isEmpty());
        Assertions.assertFalse(personalHomeworkRepository.findByIdStudentUsername(registeredUsername).isEmpty());
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_할당_실패() throws Exception {
        //given
        List<String> users = new ArrayList<String>();
        users.add("테스트코드");
        HomeworkRequest homeworkRequest = HomeworkRequest.builder()
                .title("TestTitle")
                .content("TestContent")
                .deadline(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY, LocalDateTime.now().getDayOfMonth()+1, 10, 10, 30))
                .usernames(users)
                .build();
        //when
        ResultActions resultActions = requestAssignmentHomework(homeworkRequest);
        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestAssignmentHomework(HomeworkRequest dto) throws Exception {
        return requestMvc(post("/homework"), dto);
    }

    // TODO 숙제 제출 (숙제 다시 제출 포함)

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_제출_파일없음_성공() throws Exception {
        //given
        int id = makeHomeworkList(1).get(0).getId();
        PersonalHomeworkRequest personalHomeworkRequest = PersonalHomeworkRequest.builder()
                .content("SubmitContent")
                .files(null)
                .build();
        //when
        ResultActions resultActions = requestSubmitHomework(id, personalHomeworkRequest);
        //then
        resultActions.andExpect(status().isCreated());

        PersonalHomework personalHomework =
                personalHomeworkRepository.findById(new PersonalHomeworkPk(id, registeredUsername))
                        .orElseThrow(()-> new HomeworkNotFoundException(id, registeredUsername));

        Assertions.assertEquals(personalHomework.getContent(), "SubmitContent");
        Assertions.assertEquals(personalHomework.getStatus(), PersonalHomeworkStatus.SUBMITTED);
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_제출_파일없음_실패() throws Exception {
        //given
        PersonalHomeworkRequest personalHomeworkRequest = PersonalHomeworkRequest.builder()
                .content("SubmitContent")
                .files(null)
                .build();
        //when
        ResultActions resultActions = requestSubmitHomework(1, personalHomeworkRequest);
        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    private ResultActions requestSubmitHomework(int id, PersonalHomeworkRequest dto) throws Exception {
        return requestMvc(put("/homework/" + id + "/personal-homework"), dto);
    }
    
    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_재제출_파일없음_성공() throws Exception{
        //given
        int id = makeHomeworkList(1).get(0).getId();
        PersonalHomeworkRequest personalHomeworkRequest = PersonalHomeworkRequest.builder()
                .content("ReSubmitContent")
                .files(null)
                .build();
        //when
        ResultActions resultActions = requestReSubmitHomework(id, personalHomeworkRequest);
        //then
        resultActions.andExpect(status().isOk());

        PersonalHomework personalHomework =
                personalHomeworkRepository.findById(new PersonalHomeworkPk(id, registeredUsername))
                        .orElseThrow(()-> new HomeworkNotFoundException(id, registeredUsername));

        Assertions.assertEquals(personalHomework.getContent(), "ReSubmitContent");
        Assertions.assertEquals(personalHomework.getStatus(), PersonalHomeworkStatus.SUBMITTED);
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_재제출_파일없음_실패() throws Exception{
        //given
        PersonalHomeworkRequest personalHomeworkRequest = PersonalHomeworkRequest.builder()
                .content("ReSubmitContent")
                .files(null)
                .build();
        //when
        ResultActions resultActions = requestReSubmitHomework(1, personalHomeworkRequest);
        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    private ResultActions requestReSubmitHomework(int id, PersonalHomeworkRequest dto) throws Exception {
        return requestMvc(patch("/homework/" + id + "/personal-homework"), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_상태_변경_성공() throws Exception {
        //given
        List<Homework> homeworks = makeHomeworkList(2);
        UserRequest userRequest1 = UserRequest.builder()
                .username(registeredUsername)
                .status("RETURN")
                .build();
        UserRequest userRequest2 = UserRequest.builder()
                .username(registeredUsername)
                .status("FINISH")
                .build();
        //when
        ResultActions resultActions1 = requestChangeHomeworkStatus(homeworks.get(0).getId(), userRequest1);
        ResultActions resultActions2 = requestChangeHomeworkStatus(homeworks.get(1).getId(), userRequest2);
        //then
        resultActions1.andExpect(status().isOk());
        resultActions2.andExpect(status().isOk());

        PersonalHomework personalHomework1 = personalHomeworkRepository
                .findById(new PersonalHomeworkPk(homeworks.get(0).getId(), registeredUsername))
                .orElseThrow(()->new HomeworkNotFoundException(homeworks.get(0).getId()));
        PersonalHomework personalHomework2 = personalHomeworkRepository
                .findById(new PersonalHomeworkPk(homeworks.get(1).getId(), registeredUsername))
                .orElseThrow(()->new HomeworkNotFoundException(homeworks.get(1).getId()));

        Assertions.assertEquals(personalHomework1.getStatus().name(), "RETURNED");
        Assertions.assertEquals(personalHomework2.getStatus().name(), "FINISHED");
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_상태_변경_실패() throws Exception {
        //given
        UserRequest userRequest = UserRequest.builder()
                .username(registeredUsername)
                .status("RETURN")
                .build();
        //when
        ResultActions resultActions = requestChangeHomeworkStatus(1, userRequest);
        //then
        resultActions.andExpect(status().is4xxClientError());
    }

    private ResultActions requestChangeHomeworkStatus(int id, UserRequest dto) throws Exception {
        return requestMvc(post("/homework/" + id + "/personal-homework"), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_변경_성공() throws Exception{
        //given
        Homework homework = makeHomeworkList(1).get(0);
        List<String> users = new ArrayList<>();
        users.add(registeredUsername);
        HomeworkRequest homeworkRequest = HomeworkRequest.builder()
                .title("ChangeTitle")
                .content("ChangeContent")
                .deadline(LocalDateTime.now())
                .usernames(users)
                .build();
        //when
        ResultActions resultActions = requestChangeHomework(homework.getId(), homeworkRequest);
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        Homework changedHomework = homeworkRepository.findById(homework.getId()).orElseThrow(()-> new HomeworkNotFoundException(homework.getId()));
        Assertions.assertEquals(changedHomework.getTitle(), "ChangeTitle");
        Assertions.assertEquals(changedHomework.getContent(), "ChangeContent");
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_변경_실패() throws Exception{
        //given
        List<String> users = new ArrayList<>();
        users.add(registeredUsername);
        HomeworkRequest homeworkRequest = HomeworkRequest.builder()
                .title("ChangeTitle")
                .content("ChangeContent")
                .deadline(LocalDateTime.now())
                .usernames(users)
                .build();
        //when
        ResultActions resultActions = requestChangeHomework(1, homeworkRequest);
        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestChangeHomework(int id, HomeworkRequest dto) throws Exception {
        return requestMvc(patch("/homework/" + id), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_삭제_성공() throws Exception {
        //given
        Homework homework = makeHomeworkList(1).get(0);

        //when
        ResultActions resultActions = requestDeleteHomework(homework.getId());
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());

        Assertions.assertTrue(homeworkRepository.findById(homework.getId()).isEmpty());
        Assertions.assertTrue(personalHomeworkRepository.findByIdHomeworkId(homework.getId()).isEmpty());
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 숙제_삭제_실패() throws Exception {
        //given
        //when
        ResultActions resultActions = requestDeleteHomework(1);
        //then
        resultActions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    private ResultActions requestDeleteHomework(int id) throws Exception {
        return requestMvc(delete("/homework/" + id));
    }
}
