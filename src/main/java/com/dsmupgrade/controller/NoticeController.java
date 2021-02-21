package com.dsmupgrade.controller;

import com.dsmupgrade.dto.response.NoticeResponse;
import com.dsmupgrade.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/notification/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping
    public void noticeWrite(@RequestParam String title, @RequestParam MultipartFile file, @RequestParam String content) {
        noticeService.noticeWrite(title, file, content);
    }

    @GetMapping("{id}")
    public NoticeResponse getNotice(@PathVariable int id) {
        return noticeService.getNotice(id);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
        return noticeService.downloadFile(fileName);
    }

    @PatchMapping("{id}")
    public void noticeUpdate(@RequestParam String title,
                             @RequestParam MultipartFile file,
                             @RequestParam String content,
                             @PathVariable int id) {
        noticeService.noticeUpdate(title, file, content, id);
    }
}
