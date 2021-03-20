package com.dsmupgrade.domain.student;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.student.dto.request.SignUpRequest;
import com.dsmupgrade.global.error.exception.ErrorCode;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SignUpApiTest extends IntegrationTest {

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        String username = "dkssud9556";
        String password = "@Passw0rd";
        String studentNum = "3402";
        String name = "김대웅";
        int fieldId = 1;
        SignUpRequest dto = new SignUpRequest(username, password, studentNum, name, fieldId);

        //when
        ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isOk());
    }

    @Test
    public void 회원가입_없는분야() throws Exception {
        //given
        String username = "dkssud9556";
        String password = "@Passw0rd";
        String studentNum = "3402";
        String name = "김대웅";
        int fieldId = Integer.MAX_VALUE;
        SignUpRequest dto = new SignUpRequest(username, password, studentNum, name, fieldId);

        //when
        ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(ErrorCode.FIELD_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.FIELD_NOT_FOUND.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.FIELD_NOT_FOUND.getCode()))
                .andExpect(jsonPath("errors").isEmpty());
    }

    @Test
    public void 회원가입_유효하지않은_파라미터() throws Exception {
        //given
        String username = "short";
        String password = "onlyenglish";
        String studentNum = "2446";
        String name = "이름긴사람";
        int fieldId = 1;
        SignUpRequest dto = new SignUpRequest(username, password, studentNum, name, fieldId);

        //when
        ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("errors").isNotEmpty());
    }

    private ResultActions requestSignUp(SignUpRequest dto) throws Exception {
        return requestMvc(post("/auth/sign-up"), dto);
    }
}
