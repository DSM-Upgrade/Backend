package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.domain.student.dto.request.PasswordRequest;
import com.dsmupgrade.global.error.exception.StudentNotFoundException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class StudentUpdateServiceTest extends StudentServiceTest {

    @InjectMocks
    private StudentUpdateService studentUpdateService;

    @Mock
    private ImageUploader imageUploader;

    @Test(expected = StudentNotFoundException.class)
    public void 비밀번호_변경_학생없음() {
        //given
        String username = student.getUsername();
        PasswordRequest dto = new PasswordRequest("now_password", "new_password");

        given(studentRepository.findByUsername(username)).willReturn(Optional.empty());

        //when
        studentUpdateService.updateStudentPassword(username, dto);
    }

    @Test
    public void 프로필_이미지_변경() throws IOException {
        //given
        String username = student.getUsername();
        MultipartFile file = createEmptyMultipartFile();

        given(imageUploader.upload(any(), any(), any())).willReturn(username);

        //when
        String uploadedFileName = studentUpdateService.updateStudentProfile(username, file);

        //then
        assertThat(uploadedFileName).isEqualTo(username);
    }

    private MultipartFile createEmptyMultipartFile() {
        return new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };
    }
}
