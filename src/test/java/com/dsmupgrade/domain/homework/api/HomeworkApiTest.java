package com.dsmupgrade.domain.homework.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.AssignmentHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ChangeHomeworkRequest;
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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Homework addHomework(LocalDateTime deadline){
        Homework homework = Homework.builder()
                .title("test")
                .content("test")
                .createdAt(LocalDateTime.now())
                .deadline(deadline)
                .homeworkFile(null)
                .build();
        homeworkRepository.save(homework);
        return homework;
    }

    private void addPersonalHomework(Homework homework){
        List<String> usernameList = new ArrayList<String>();
        usernameList.add(registeredUsername);
        for (String user : usernameList){
            PersonalHomework personalHomework = PersonalHomework.builder()
                    .studentUsername(user)
                    .status(PersonalHomeworkStatus.ASSIGNED)
                    .submittedAt(null)
                    .content("test")
                    .homework(homework)
                    .personalHomeworkFile(null)
                    .build();
            personalHomeworkRepository.save(personalHomework);
        }
    }

    @After
    public void clenaup(){
        personalHomeworkRepository.deleteAll();
        homeworkRepository.deleteAll();
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
    public void 유저_할당된_숙제리스트() throws Exception {
        //given
        List<Homework> homeworks = makeHomeworkList(10);
        //when
        ResultActions resultActions = requestGetUserHomeworkList(registeredUsername);

        //then
        MvcResult result = resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        List<UserAllHomeworkListResponse> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<List<UserAllHomeworkListResponse>>() {});
        for (int i=0; i<response.size(); i++) {
            Assertions.assertEquals(response.get(i).getHomeworkId(), homeworks.get(i).getId());
            Assertions.assertEquals(response.get(i).getHomeworkStart(), homeworks.get(i).getCreatedAt());
            Assertions.assertEquals(response.get(i).getHomeworkEnd(), homeworks.get(i).getDeadline());
            Assertions.assertEquals(response.get(i).getHomeworkTitle(), homeworks.get(i).getTitle());
            Assertions.assertEquals(response.get(i).getHomeworkContent(), homeworks.get(i).getContent());
            if(response.get(i).getHomeworkEnd().isBefore(LocalDateTime.now()))
                Assertions.assertEquals(response.get(i).getHomeworkStatus(), "UN_SUBMITTED");
            else Assertions.assertEquals(response.get(i).getHomeworkStatus(), "ASSIGNED");
        }
    }

    private ResultActions requestGetUserHomeworkList(String username) throws Exception {
        return requestMvc(get("/homework/list/"));
    }

    @Test
    @WithMockUser(username = registeredUsername)
    public void 유저_할당된_숙제내용() throws Exception {
        //given
        List<Homework> homeworks = makeHomeworkList(10);
        List<PersonalHomework> personalHomeworks = homeworks.stream().map(
                (homework)->{
                    return personalHomeworkRepository.findByHomeworkId(homework.getId()).get();
                }
        ).collect(Collectors.toList());

        for(int i=0; i<=9; i++) {
        //when
            ResultActions resultActions = requestGetUserHomeworkContent(homeworks.get(i).getId());
        //then
            MvcResult result = resultActions
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();
            UserHomeworkResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UserHomeworkResponse.class);
            Assertions.assertEquals(response.getHomeworkTitle(), homeworks.get(i).getTitle());
            Assertions.assertEquals(response.getHomeworkStart(), homeworks.get(i).getCreatedAt());
            Assertions.assertEquals(response.getHomeworkEnd(), homeworks.get(i).getDeadline());
            Assertions.assertEquals(response.getHomeworkSubmittedDate(), personalHomeworks.get(i).getSubmittedAt());
            Assertions.assertEquals(response.getHomeworkContent(), homeworks.get(i).getContent());
            Assertions.assertEquals(response.getHomeworkReturn(), personalHomeworks.get(i).getContent());
            Assertions.assertEquals(response.getHomeworkFileName(), homeworks.get(i).getHomeworkFile());
            Assertions.assertEquals(response.getPersonalHomeworkFileName(), personalHomeworks.get(i).getPersonalHomeworkFile());
        }
    }

    private ResultActions requestGetUserHomeworkContent(int homeworkId) throws Exception {
        return requestMvc(get("/homework/content/" + homeworkId));
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 파일없는_숙제할당() throws Exception{
        //given
        List<String> userName = new ArrayList<>();
        userName.add(registeredUsername);
        AssignmentHomeworkRequest assignmentHomeworkRequest = AssignmentHomeworkRequest.builder()
                .username(userName)
                .homeworkTitle("test")
                .homeworkContent("test")
                .deadline(LocalDateTime.of(2030, Month.JANUARY, 1, 10, 10, 30))
                .homeworkFile(null)
                .build();
        //when
        ResultActions resultActions = assignmentHomework(assignmentHomeworkRequest);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());

        Homework homework = homeworkRepository.findAll().get(0);
        PersonalHomework personalHomework = personalHomeworkRepository.findByStudentUsername(registeredUsername).get(0);

        Assertions.assertEquals(homework.getTitle(), assignmentHomeworkRequest.getHomeworkTitle());
        Assertions.assertEquals(homework.getContent(), assignmentHomeworkRequest.getHomeworkContent());
        Assertions.assertEquals(homework.getHomeworkFile(), null);
        Assertions.assertEquals(personalHomework.getStudentUsername(), assignmentHomeworkRequest.getUsername().get(0));
        Assertions.assertEquals(personalHomework.getStatus(), PersonalHomeworkStatus.ASSIGNED);
        Assertions.assertEquals(personalHomework.getContent(), null);
        Assertions.assertEquals(personalHomework.getPersonalHomeworkFile(), null);
    }

    private ResultActions assignmentHomework(AssignmentHomeworkRequest dto) throws Exception {
        return requestMvc(post("/homework/assignment"), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 숙제반환() throws Exception{
        //given
        Homework homework = this.addHomework(LocalDateTime.of(2030, Month.JANUARY, 1, 10, 10, 30));
        this.addPersonalHomework(homework);
        int homeworkId = homework.getId();
        ReturnHomeworkRequest returnHomeworkRequest = ReturnHomeworkRequest.builder()
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
        Homework homework = this.addHomework(LocalDateTime.of(2030, Month.JANUARY, 1, 10, 10, 30));
        this.addPersonalHomework(homework);
        CompletionHomeworkRequest completionHomeworkRequest = CompletionHomeworkRequest.builder()
                .username(registeredUsername)
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
    public void 숙제변경_파일없이() throws Exception{
        //given
        Homework homework = this.addHomework(LocalDateTime.of(2030, Month.JANUARY, 1, 10, 10, 30));
        this.addPersonalHomework(homework);
        List<String> user = new ArrayList<>();
        user.add(registeredUsername);
        ChangeHomeworkRequest changeHomeworkRequest = ChangeHomeworkRequest.builder()
                .homeworkId(homework.getId())
                .username(user)
                .homeworkTitle("newTest")
                .homeworkContent("newTest")
                .deadline(LocalDateTime.of(2030, Month.JANUARY, 1, 10, 10, 30))
                .homeworkFile(null)
                .build();
        //when
        ResultActions resultActions = changeHomework(changeHomeworkRequest);
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertEquals(homeworkRepository.findById(homework.getId()).get().getTitle(), "newTest");
        Assertions.assertEquals(homeworkRepository.findById(homework.getId()).get().getContent(), "newTest");
    }

    private ResultActions changeHomework(ChangeHomeworkRequest dto) throws Exception {
        return requestMvc(patch("/homework/change"), dto);
    }

    @Test
    @WithMockUser(username = registeredUsername, roles = { "ADMIN" })
    public void 숙제삭제_파일없이() throws Exception{
        //given
        Homework homework = this.addHomework(LocalDateTime.of(2030, Month.JANUARY, 1, 10, 10, 30));
        this.addPersonalHomework(homework);
        //when
        ResultActions resultActions = deleteHomework(homework.getId());
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
        Assertions.assertTrue(homeworkRepository.findById(homework.getId()).isEmpty());
        Assertions.assertTrue(personalHomeworkRepository.findByStudentUsernameAndHomework(registeredUsername, homework).isEmpty());
    }

    private ResultActions deleteHomework(int homeworkId) throws Exception {
        return requestMvc(delete("/homework/elimination/" + homeworkId));
    }
}
