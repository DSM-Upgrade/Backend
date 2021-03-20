package com.dsmupgrade.domain.student.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Profile("test")
@Service
public class MockImageUploader implements ImageUploader {

    @Override
    public String upload(String username, MultipartFile multipartFile, String dir) throws IOException {
        return username;
    }
}
