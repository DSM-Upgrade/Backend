package com.dsmupgrade.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class S3ImageUploader implements ImageUploader {

    private final AmazonS3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(String username, MultipartFile multipartFile, String dir) throws IOException {
        validateFileType(multipartFile);

        File uploadFile = multipartToFile(multipartFile)
                .orElseThrow();

        return upload(username, uploadFile, dir);
    }

    private void validateFileType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            // TODO add exception
        } else if (!contentType.split("/").equals("image")) {
            // TODO add exception
        }
    }

    private String upload(String username, File uploadFile, String dir) {
        String filename = dir + "/" + username;
        return putS3(uploadFile, filename);
    }

    private String putS3(File uploadFile, String filename) {
        s3Client.putObject(new PutObjectRequest(bucket, filename, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, filename).toString();
    }

    private Optional<File> multipartToFile(MultipartFile multipart) throws IOException {
        File file = new File(multipart.getOriginalFilename());
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipart.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }
}
