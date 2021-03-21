package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.domain.student.dto.request.PasswordRequest;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class StudentUpdateServiceTest extends StudentServiceTest {

    @InjectMocks
    private StudentUpdateService studentUpdateService;

    @Test(expected = StudentNotFoundException.class)
    public void 비밀번호_변경_학생없음() {
        //given
        String username = student.getUsername();
        PasswordRequest dto = new PasswordRequest("now_password", "new_password");

        given(studentRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        studentUpdateService.updateStudentPassword(username, dto);
    }
}
