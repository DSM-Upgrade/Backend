package com.dsmupgrade.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Boolean isSubmitted;
}
