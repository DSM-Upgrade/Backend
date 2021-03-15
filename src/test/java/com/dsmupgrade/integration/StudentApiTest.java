package com.dsmupgrade.integration;

import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.test.IntegrationTest;
import com.dsmupgrade.test.setup.domain.StudentSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentApiTest extends IntegrationTest {

    @Autowired
    private StudentSetup studentSetup;

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        final Student student = studentSetup.build();

    }
}
