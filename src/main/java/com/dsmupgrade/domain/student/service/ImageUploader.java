package com.dsmupgrade.domain.student.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploader {

    String upload(String username, MultipartFile multipartFile, String dir) throws IOException;
}
