package com.dsmupgrade.domain.fine.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setIsSubmitted(Boolean isSubmitted){
        this.isSubmitted = isSubmitted;
    }
}
