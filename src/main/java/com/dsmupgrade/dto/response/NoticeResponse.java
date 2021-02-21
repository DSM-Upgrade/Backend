package com.dsmupgrade.dto.response;


import com.dsmupgrade.domain.entity.notice.Notice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class NoticeResponse {
    private LocalDateTime createdAt;

    private String title;

    private String content;

    private String fileName;

    public static NoticeResponse of(Notice notice){
        return NoticeResponse.builder()
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .fileName(notice.getFileName())
                .title(notice.getTitle())
                .build();
    }

}
