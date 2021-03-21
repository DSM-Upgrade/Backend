package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.ServiceTest;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentBuilder;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import org.junit.Before;
import org.mockito.Mock;

public class StudentServiceTest extends ServiceTest {

    @Mock
    protected StudentRepository studentRepository;
    protected Student student;

    @Before
    public void setUp() throws Exception {
        student = StudentBuilder.build();
    }
}
