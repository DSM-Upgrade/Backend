package com.dsmupgrade.domain.homework.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Homework {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date deadline;
}
