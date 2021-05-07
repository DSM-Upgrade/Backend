package com.dsmupgrade.domain.fine.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime date;

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
