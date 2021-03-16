package com.dsmupgrade.domain.student.controller;

import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentBuilder;
import com.dsmupgrade.domain.student.dto.request.LoginRequest;
import com.dsmupgrade.domain.student.dto.request.LoginRequestBuilder;
import com.dsmupgrade.domain.student.dto.request.SignUpRequest;
import com.dsmupgrade.domain.student.dto.request.SignUpRequestBuilder;
import com.dsmupgrade.test.IntegrationTest;
import com.dsmupgrade.test.setup.domain.FieldSetup;
import com.dsmupgrade.test.setup.domain.StudentSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentApiTest extends IntegrationTest {

    @Autowired
    private StudentSetup studentSetup;

    @Autowired
    private FieldSetup fieldSetup;

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        fieldSetup.save();
        final Student student = StudentBuilder.build();
        final SignUpRequest dto = SignUpRequestBuilder.buildFrom(student);

        //when
        final ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입_잘못된학번() throws Exception {
        //given
        final String username = "dkssud9556";
        final String password = "@Passw0rd";
        final String studentNum = "3501";
        final String name = "김대웅";
        final int fieldId = 1;
        final SignUpRequest dto = SignUpRequestBuilder.build(username, password, studentNum, name, fieldId);

        //when
        final ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 로그인() throws Exception {
        //given
        final Student student = studentSetup.save();
        final LoginRequest dto = LoginRequestBuilder.buildFrom(student);

        //when
        ResultActions resultActions = requestLogin(dto);

        //then
        resultActions
                .andExpect(status().isOk());
    }

    private ResultActions requestLogin(LoginRequest dto) throws Exception {
        return mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

    private ResultActions requestSignUp(SignUpRequest dto) throws Exception {
        return mvc.perform(post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }
}
