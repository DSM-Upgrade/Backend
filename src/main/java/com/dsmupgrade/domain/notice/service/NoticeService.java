package com.dsmupgrade.domain.notice.service;


import com.dsmupgrade.domain.notice.dto.response.NoticeResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface NoticeService {
    void noticeWrite(String title, MultipartFile file, String content);

    void noticeUpdate(String title, MultipartFile file, String content, Integer id);

    ResponseEntity<Resource> downloadFile(String fileName) throws IOException;

    NoticeResponse getNotice(Integer id);
}
