package com.dsmupgrade.global;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public abstract class S3FileUploader implements FileUploader {

    private AmazonS3Client s3Client;

    public S3FileUploader(AmazonS3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Value("${aws.s3.bucket}")
    private String bucket;

    public String upload(String username, MultipartFile multipartFile, String dir) throws IOException {
        validateFileType(multipartFile);

        File uploadFile = multipartToFile(multipartFile, username)
                .orElseThrow();

        String filename = resolveFileName(multipartFile, username);
        return uploadS3(filename, uploadFile, dir);
    }

    abstract protected void validateFileType(MultipartFile multipartFile);

    abstract protected String resolveFileName(MultipartFile multipart, String username);

    protected Optional<File> multipartToFile(MultipartFile multipart, String username) throws IOException {
        File file = new File(resolveLocalFilePath(multipart, username));
        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipart.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    abstract protected String resolveLocalFilePath(MultipartFile multipart, String username);

    protected String uploadS3(String filename, File uploadFile, String dir) {
        String fullFilename = dir + "/" + filename;
        return putS3(uploadFile, fullFilename);
    }

    protected String putS3(File uploadFile, String filename) {
        s3Client.putObject(new PutObjectRequest(bucket, filename, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        removeLocalFile(uploadFile);
        return s3Client.getUrl(bucket, filename).toString();
    }

    protected void removeLocalFile(File file) {
        file.delete();
    }
}
