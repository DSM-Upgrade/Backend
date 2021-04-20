package com.dsmupgrade.domain.student.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dsmupgrade.global.S3FileUploader;
import com.dsmupgrade.global.error.exception.InvalidFileTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class S3ImageUploader extends S3FileUploader {

    @Autowired
    public S3ImageUploader(AmazonS3Client s3Client) {
        super(s3Client);
    }

    protected void validateFileType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null || !contentType.split("/")[0].equals("image")) {
            throw new InvalidFileTypeException();
        }
    }
}
