package com.dsmupgrade.dto.response;

import com.dsmupgrade.domain.entity.notice.Notice;
import com.dsmupgrade.domain.entity.vote.Vote;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class VoteResponse {
    private final LocalDateTime createdAt;

    private final LocalDateTime deadLine;

    private final String title;

    private final String[] content;

    private final boolean isEven;

    private final boolean isDead;

    public static VoteResponse of(Vote vote,String[] content){
        return VoteResponse.builder()
                .title(vote.getTitle())
                .content(content)
                .createdAt(vote.getCreatedAt())
                .isDead(vote.isDead())
                .isEven(vote.isEven())
                .deadLine(vote.getDeadLine())
                .build();
    }
}
