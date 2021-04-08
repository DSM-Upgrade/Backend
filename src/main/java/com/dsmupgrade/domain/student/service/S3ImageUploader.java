package com.dsmupgrade.domain.student.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dsmupgrade.global.S3FileUploader;
import com.dsmupgrade.global.error.exception.InvalidFileTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RequiredArgsConstructor
@Component
public class S3ImageUploader extends S3FileUploader {

    private final AmazonS3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    protected void validateFileType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null || !contentType.split("/")[0].equals("image")) {
            throw new InvalidFileTypeException();
        }
    }

    protected String putS3(File uploadFile, String filename) {
        s3Client.putObject(new PutObjectRequest(bucket, filename, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        removeLocalFile(uploadFile);
        return s3Client.getUrl(bucket, filename).toString();
    }
}
