package com.dsmupgrade.domain.notice.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private String filePath;

    private String fileName;

    private LocalDateTime createdAt;

    public Notice update(String title, String fileName, String filePath, String content, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
        this.filePath = filePath;
        this.createdAt = createdAt;
        return this;
    }
}

