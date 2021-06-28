package com.dsmupgrade.domain.vote.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "vote")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private boolean isEven;

    private boolean isDead;

    private Integer count;

    private LocalDateTime createdAt;

    private LocalDateTime deadLine;

    public Vote update(String title, boolean isEven, Integer count, LocalDateTime deadLine, LocalDateTime createdAt) {
        this.title = title;
        this.isEven = isEven;
        this.count = count;
        this.deadLine = deadLine;
        this.createdAt = createdAt;

        return this;
    }

    public Vote deadIsDead() {
        this.isDead = true;
        return this;
    }

}
