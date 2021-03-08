package com.dsmupgrade.test.setup.domain;

import com.dsmupgrade.domain.field.domain.Field;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.test.config.TestProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Profile(TestProfile.TEST)
@RequiredArgsConstructor
@Component
public class StudentSetup {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public Student save() {
        final Student student = buildStudent("3401");
        return studentRepository.save(student);
    }

    public List<Student> save(int count) {
        List<Student> students = new ArrayList<>();
        IntStream.range(0, count).forEach(i -> students.add(
                studentRepository.save(buildStudent(String.format("34%02d", i))))
        );
        return students;
    }

    public Student build() {
        return buildStudent("3401");
    }

    private Student buildStudent(String studentNum) {
        return Student.builder()
                .studentNum(studentNum)
                .field(Field.builder()
                        .id(1)
                        .name("백엔드")
                        .build())
                .name("김대웅")
                .username("dkssud9556")
                .password(passwordEncoder.encode("testpass123"))
                .build();
    }
}
