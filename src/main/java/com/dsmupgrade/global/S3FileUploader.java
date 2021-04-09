package com.dsmupgrade.global;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public abstract class S3FileUploader implements FileUploader {

    @Value("${image.file.path}")
    private String filePath;

    public String upload(String username, MultipartFile multipartFile, String dir) throws IOException {
        validateFileType(multipartFile);

        File uploadFile = multipartToFile(multipartFile)
                .orElseThrow();

        String filename = username + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        return uploadS3(filename, uploadFile, dir);
    }

    abstract protected void validateFileType(MultipartFile multipartFile);

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

    protected String uploadS3(String filename, File uploadFile, String dir) {
        String fullFilename = dir + "/" + filename;
        return putS3(uploadFile, fullFilename);
    }

    abstract protected String putS3(File uploadFile, String filename);

    protected void removeLocalFile(File file) {
        file.delete();
    }
}
