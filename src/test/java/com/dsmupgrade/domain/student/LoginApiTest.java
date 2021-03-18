package com.dsmupgrade.domain.student;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.student.dto.request.LoginRequest;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginApiTest extends IntegrationTest {

    @Test
    public void 로그인_등록안됨() throws Exception {
        //given
        String username = "nonregister123";
        String password = "@Passw0rd";
        LoginRequest dto = new LoginRequest(username, password);

        //when
        ResultActions resultActions = requestLogin(dto);

        //then
        resultActions
                .andExpect(status().isForbidden());
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
                .andExpect(status().isOk());
    }

    private ResultActions requestLogin(LoginRequest dto) throws Exception {
        return requestMvc(post("/auth"), dto);
    }
}
