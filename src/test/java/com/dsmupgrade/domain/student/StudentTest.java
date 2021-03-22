package com.dsmupgrade.domain.student;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.student.domain.Student;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    private static final String username = "dkssud9556";
    private static final String password = "@Passw0rd";
    private static final String studentNum = "3401";
    private static final String name = "김대웅";
    private static final Field field = Field.builder()
            .id(1)
            .name("백엔드")
            .build();

    @Test
    public void 학생_생성() {
        Student student = createStudent();

        assertThat(student.getUsername()).isEqualTo(username);
        assertThat(student.getPassword()).isEqualTo(password);
        assertThat(student.getStudentNum()).isEqualTo(studentNum);
        assertThat(student.getName()).isEqualTo(name);
        assertThat(student.getField()).isEqualTo(field);
        assertThat(student.getIsAdmin()).isFalse();
        assertThat(student.getIsFineManager()).isFalse();
        assertThat(student.getIsNoticeManager()).isFalse();
        assertThat(student.getIsRegistered()).isFalse();
    }

    @Test
    public void 학생_등록() {
        Student student = createStudent();

        student.register();

        assertThat(student.getIsRegistered()).isTrue();
    }

    @Test
    public void 학생_공지사항_권한_부여() {
        Student student = createStudent();

        student.noticeManager();

        assertThat(student.getIsNoticeManager()).isTrue();
    }

    @Test
    public void 학생_벌금_권한_부여() {
        Student student = createStudent();

        student.fineManager();

        assertThat(student.getIsFineManager()).isTrue();
    }

    private static Student createStudent() {
        return Student.builder()
                .username(username)
                .password(password)
                .studentNum(studentNum)
                .name(name)
                .field(field)
                .build();
    }
}
