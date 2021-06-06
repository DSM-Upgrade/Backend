package com.dsmupgrade.domain.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HomeworkFile {
    @EmbeddedId
    private HomeworkFilePk id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id")
    private PersonalHomework personalHomework;


}
