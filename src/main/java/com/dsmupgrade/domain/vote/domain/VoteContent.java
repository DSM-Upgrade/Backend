package com.dsmupgrade.domain.vote.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "voteId")
    private Vote vote;

    public VoteContent update(String content, Vote vote) {
        this.content = content;
        this.vote = vote;

        return this;
    }
}
