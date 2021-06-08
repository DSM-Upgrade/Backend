package com.dsmupgrade.domain.homework.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.HomeworkRequest;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkListResponse;
import com.dsmupgrade.global.error.exception.HomeworkNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void 숙제_리스트_받아오기_파일없음_성공() throws Exception {
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
    public void 숙제_내용_받아오기_파일없음_성공() throws Exception {
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
    // TODO 숙제 재 제출
    // TODO 숙제 상태 변경(완료 or 반환)
    // TODO 할당한 숙제의 내용을 변경
    // TODO 숙제를 삭제

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

    private ResultActions requestDeleteHomework(int id) throws Exception {
        return requestMvc(delete("/homework/" + id));
    }
}
