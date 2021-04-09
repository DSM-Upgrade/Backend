package com.dsmupgrade.global;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploader {

    String upload(String username, MultipartFile multipartFile, String dir) throws IOException;
}
