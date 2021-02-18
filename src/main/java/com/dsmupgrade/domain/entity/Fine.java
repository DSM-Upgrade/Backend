package com.dsmupgrade.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Fine {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String is_submitted;
}
