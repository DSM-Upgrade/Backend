package com.dsmupgrade.domain.entity.vote;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    private int count;

    private LocalDateTime createdAt;

    private LocalDateTime deadLine;


    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<VoteContent> voteContent = new ArrayList<>();


}
