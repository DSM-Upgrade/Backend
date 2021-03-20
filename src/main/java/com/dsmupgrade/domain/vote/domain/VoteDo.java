package com.dsmupgrade.domain.vote.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteDo {
    @Id
    private Integer id;

    private String username;

    private Integer voteId;

    private Integer voteContentId;
}
