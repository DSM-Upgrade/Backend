package com.dsmupgrade.domain.homework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
