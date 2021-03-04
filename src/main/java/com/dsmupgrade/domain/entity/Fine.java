package com.dsmupgrade.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    @Column(length = 45,nullable = false)
    private String reason;

    @Column(length = 16,nullable = false)
    private String username;

    @Column(length = 1,nullable = false)
    private Boolean isSubmitted;
}
