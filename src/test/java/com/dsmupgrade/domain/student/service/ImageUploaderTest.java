package com.dsmupgrade.domain.student.service;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class ImageUploaderTest extends StudentServiceTest {

    @InjectMocks
    private ImageUploader imageUploader;

    @Mock
    private AmazonS3Client amazonS3Client;
}
