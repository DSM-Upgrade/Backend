package com.dsmupgrade.domain.student;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.student.dto.request.LoginRequest;
import com.dsmupgrade.global.error.exception.ErrorCode;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginApiTest extends IntegrationTest {

    @Test
    public void 로그인_등록안됨() throws Exception {
        //given
        String username = "unregister123";
        String password = "@Passw0rd";
        LoginRequest dto = new LoginRequest(username, password);

        //when
        ResultActions resultActions = requestLogin(dto);

        //then
        resultActions
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("message").value(ErrorCode.STUDENT_NOT_REGISTERED.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.STUDENT_NOT_REGISTERED.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.STUDENT_NOT_REGISTERED.getCode()))
                .andExpect(jsonPath("errors").isEmpty());
        throw new Exception();
    }

    @Test
    public void 로그인_성공() throws Exception {
        //given
        String username = "register123";
        String password = "@Passw0rd";
        LoginRequest dto = new LoginRequest(username, password);

        //when
        ResultActions resultActions = requestLogin(dto);

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").isNotEmpty())
                .andExpect(jsonPath("refresh_token").isNotEmpty());
    }

    @Test
    public void 로그인_없는유저() throws Exception {
        //given
        String username = "nonexistent1";
        String password = "@Passw0rd";
        LoginRequest dto = new LoginRequest(username, password);

        //when
        ResultActions resultActions = requestLogin(dto);

        //then
        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_LOGIN_INFO.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.INVALID_LOGIN_INFO.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_LOGIN_INFO.getCode()))
                .andExpect(jsonPath("errors").isEmpty());
    }

    @Test
    public void 로그인_잘못된_비밀번호() throws Exception {
        //given
        String username = "register123";
        String password = "P@ssw0rd";
        LoginRequest dto = new LoginRequest(username, password);

        //when
        ResultActions resultActions = requestLogin(dto);

        //then
        resultActions
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_LOGIN_INFO.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.INVALID_LOGIN_INFO.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_LOGIN_INFO.getCode()))
                .andExpect(jsonPath("errors").isEmpty());
    }

    private ResultActions requestLogin(LoginRequest dto) throws Exception {
        return requestMvc(post("/auth"), dto);
    }
}
