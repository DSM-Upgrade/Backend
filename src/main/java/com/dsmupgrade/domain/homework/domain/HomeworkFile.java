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
@Entity(name = "homework_file")
public class HomeworkFile {
    @EmbeddedId
    private HomeworkFilePk id;

    @ManyToOne
    @MapsId("homeworkId")
    @JoinColumn(name = "homework_id")
    private Homework homework;
}
