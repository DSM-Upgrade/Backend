package com.dsmupgrade.domain.entity.vote;

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

    private Integer userId;

    private Integer voteId;

    private Integer voteContentId;
}
