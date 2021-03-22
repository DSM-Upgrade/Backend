package com.dsmupgrade.domain.student.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dsmupgrade.global.error.exception.InvalidFileTypeException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class ImageUploaderTest extends StudentServiceTest {

    @InjectMocks
    private S3ImageUploader imageUploader;

    @Mock
    private AmazonS3Client amazonS3Client;

    @Test
    public void 파일_타입_검사_통과() {
        //given
        MultipartFile file = createMultipartFile("image/png");

        //when
        imageUploader.validateFileType(file);
    }

    @Test(expected = InvalidFileTypeException.class)
    public void 파일_타입_검사_불통과() {
        //given
        MultipartFile file = createMultipartFile("plain/text");

        //when
        imageUploader.validateFileType(file);
    }

    private MultipartFile createMultipartFile(String contentType) {
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
                return contentType;
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
