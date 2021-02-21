package com.dsmupgrade.domain.entity.vote;

import com.dsmupgrade.domain.entity.notice.Notice;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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
