package com.dsmupgrade.domain.student.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dsmupgrade.global.error.exception.InvalidFileTypeException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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

    @Value("${image.file.path}")
    private String filePath;

    @Override
    public String upload(String username, MultipartFile multipartFile, String dir) throws IOException {
        validateFileType(multipartFile);

        File uploadFile = multipartToFile(multipartFile)
                .orElseThrow();

        String filename = username + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return upload(filename, uploadFile, dir);
    }

    protected void validateFileType(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (contentType == null || !contentType.split("/")[0].equals("image")) {
            throw new InvalidFileTypeException();
        }
    }

    protected String upload(String filename, File uploadFile, String dir) {
        String fullFilename = dir + "/" + filename;
        String url = putS3(uploadFile, fullFilename);
        removeNewFile(uploadFile);
        return url;
    }

    protected void removeNewFile(File file) {
        file.delete();
    }

    protected String putS3(File uploadFile, String filename) {
        s3Client.putObject(new PutObjectRequest(bucket, filename, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, filename).toString();
    }

    protected Optional<File> multipartToFile(MultipartFile multipart) throws IOException {
        File file = new File(filePath + "/" + multipart.getOriginalFilename());
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipart.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }
}
