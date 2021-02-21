package com.dsmupgrade.service.notice;

import com.dsmupgrade.domain.entity.notice.Notice;
import com.dsmupgrade.domain.entity.notice.NoticeRepository;
import com.dsmupgrade.domain.entity.notification.Notification;
import com.dsmupgrade.domain.entity.notification.NotificationRepository;
import com.dsmupgrade.dto.response.NoticeResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    @Value("${image.file.path}")
    private String imagePath;

    private final NoticeRepository noticeRepository;
    private final NotificationRepository notificationRepository;

    @SneakyThrows
    @Override
    public void noticeWrite(String title, MultipartFile file, String content) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Notice notice = noticeRepository.save(
                Notice.builder()
                        .title(title)
                        .content(content)
                        .filePath(imagePath + "/" + fileName)
                        .fileName(fileName)
                        .createdAt(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
                        .build()
        );
        notificationRepository.save(
                Notification.builder()
                        .isVote(false)
                        .isAttach(true)
                        .detailId(notice.getId())
                        .build()
        );
        File file2 = new File(imagePath, fileName);
        file.transferTo(file2);
    }

    @SneakyThrows
    @Override
    public void noticeUpdate(String title, MultipartFile file, String content, Integer id) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        Notification notification = notificationRepository.findById(id).orElseThrow();
        Notice notice = noticeRepository.findById(notification.getDetailId()).orElseThrow();

        File deleteFile = new File(notice.getFilePath());
        deleteFile.delete();

        noticeRepository.save(notice.update(title, fileName, imagePath + "/" + fileName, content,
                LocalDateTime.now(ZoneId.of("Asia/Seoul"))));
        File file2 = new File(imagePath, fileName);
        file.transferTo(file2);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName) throws IOException {
        Path path = Paths.get(imagePath + "/" + fileName);
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        System.out.println(fileName);
        System.out.println(resource.getFilename());
        fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @Override
    public NoticeResponse getNotice(Integer id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        Notice notice = noticeRepository.findById(notification.getId()).orElseThrow();

        return NoticeResponse.of(notice);
    }

}
