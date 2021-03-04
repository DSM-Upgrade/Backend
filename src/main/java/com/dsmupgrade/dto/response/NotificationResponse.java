package com.dsmupgrade.dto.response;

import com.dsmupgrade.domain.entity.Notification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class NotificationResponse {

    private final Integer id;

    private final String title;

    private final boolean isVote;

    private final boolean isAttach;

    private final LocalDateTime createdAt;

    public static NotificationResponse of( Notification notification,String title, LocalDateTime createdAt) {
        return NotificationResponse.builder()
                .createdAt(createdAt)
                .id(notification.getId())
                .isAttach(notification.isAttach())
                .title(title)
                .isVote(notification.isVote())
                .build();
    }
}
