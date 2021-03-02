package com.dsmupgrade.dto.response;


import com.dsmupgrade.domain.entity.Notice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class NoticeResponse {
    private final LocalDateTime createdAt;

    private final String title;

    private final String content;

    private final String fileName;

    public static NoticeResponse of(Notice notice){
        return NoticeResponse.builder()
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .fileName(notice.getFileName())
                .title(notice.getTitle())
                .build();
    }

}
