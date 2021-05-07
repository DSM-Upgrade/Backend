package com.dsmupgrade.domain.homework.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.dsmupgrade.global.S3FileUploader;
import com.dsmupgrade.global.error.exception.InvalidFileTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3HomeworkFileUploader extends S3FileUploader {

    @Value("${aws.s3.bucket}")
    private String bucket;

    public S3HomeworkFileUploader(AmazonS3Client s3Client) {
        super(s3Client);
    }

    @Override
    protected void validateFileType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null
                || !contentType.split("/")[0].equals("image")
                || !contentType.split("/")[0].equals("text")
                || !contentType.split("/")[0].equals("application")
                || !contentType.split("/")[0].equals("video")
        ) {
            throw new InvalidFileTypeException();
        }
    }

    public void delete(String filename){
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, filename);
        super.s3Client.deleteObject(deleteObjectRequest);
    }
}
