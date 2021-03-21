package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.ServiceTest;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentBuilder;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class StudentFindServiceTest extends StudentServiceTest {

    @InjectMocks
    private StudentFindService studentFindService;

    @Test(expected = StudentNotFoundException.class)
    public void 학생조회_없음() {
        //given
        String username = student.getUsername();

        given(studentRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        studentFindService.getStudentByUsername(username);
    }
}
