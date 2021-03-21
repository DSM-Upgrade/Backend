package com.dsmupgrade.domain.student.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.field.domain.FieldRepository;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.domain.student.dto.request.PasswordRequest;
import com.dsmupgrade.domain.student.dto.request.UpdateStudentRequest;
import com.dsmupgrade.global.error.exception.ErrorCode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StudentApiTest extends IntegrationTest {

    private static final String registeredUsername = "register123";
    private static final String unregisteredUsername = "unregister123";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @WithMockUser(username = "register123")
    public void 회원조회() throws Exception {
        //given
        Student student = studentRepository.findByUsername(registeredUsername)
                .orElseThrow();

        //when
        ResultActions resultActions = requestGetStudent();

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value(student.getUsername()))
                .andExpect(jsonPath("student_num").value(student.getStudentNum()))
                .andExpect(jsonPath("name").value(student.getName()))
                .andExpect(jsonPath("field").value(student.getField().getName()));
    }

    @Test
    @WithMockUser(username = "register123")
    public void 회원_비밀번호_변경() throws Exception {
        //given
        String newPassword = "P@ssw0rd";
        String nowPassword = "@Passw0rd";
        PasswordRequest dto = new PasswordRequest(nowPassword, newPassword);

        //when
        ResultActions resultActions = requestUpdatePassword(dto);

        //then
        resultActions.andExpect(status().isOk());

        Student student = findStudentByUsername(registeredUsername);
        assertThat(passwordEncoder.matches(newPassword, student.getPassword())).isTrue();
    }

    @Test
    @WithMockUser(username = "register123")
    public void 회원_비밀번호_변경_비밀번호_불일치() throws Exception {
        //given
        String newPassword = "P@ssw0rd";
        String wrongPassword = "!Passw0rd";
        PasswordRequest dto = new PasswordRequest(wrongPassword, newPassword);

        //when
        ResultActions resultActions = requestUpdatePassword(dto);

        //then
        resultActions
                .andExpect(status().isConflict())
                .andExpect(jsonPath("message").value(ErrorCode.PASSWORD_NOT_MATCHED.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.PASSWORD_NOT_MATCHED.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.PASSWORD_NOT_MATCHED.getCode()))
                .andExpect(jsonPath("errors").isEmpty());
    }

    @Test
    @WithMockUser(username = "register123")
    public void 회원_정보_변경() throws Exception {
        //given
        String newStudentNum = "2304";
        int newFieldId = 2;
        UpdateStudentRequest dto = new UpdateStudentRequest(newStudentNum, newFieldId);

        //when
        ResultActions resultActions = requestUpdateStudent(dto);

        //then
        resultActions.andExpect(status().isOk());

        Student student = findStudentByUsername(registeredUsername);
        assertThat(student.getStudentNum()).isEqualTo(newStudentNum);
        assertThat(student.getField().getId()).isEqualTo(newFieldId);
    }

    @Test
    @WithMockUser(username = "register123")
    public void 회원_정보_변경_없는분야() throws Exception {
        //given
        int nonexistentFieldId = Integer.MAX_VALUE;
        UpdateStudentRequest dto = new UpdateStudentRequest(null, nonexistentFieldId);

        //when
        ResultActions resultActions = requestUpdateStudent(dto);

        //then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(ErrorCode.FIELD_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("status").value(ErrorCode.FIELD_NOT_FOUND.getStatus()))
                .andExpect(jsonPath("code").value(ErrorCode.FIELD_NOT_FOUND.getCode()))
                .andExpect(jsonPath("errors").isEmpty());
    }

    private Student findStudentByUsername(String username) {
        return studentRepository.findByUsername(username).orElseThrow();
    }

    private ResultActions requestGetStudent() throws Exception {
        return requestMvc(get("/student"));
    }

    private ResultActions requestUpdatePassword(PasswordRequest dto) throws Exception {
        return requestMvc(patch("/student/password"), dto);
    }

    private ResultActions requestUpdateStudent(UpdateStudentRequest dto) throws Exception {
        return requestMvc(patch("/student"), dto);
    }
}
