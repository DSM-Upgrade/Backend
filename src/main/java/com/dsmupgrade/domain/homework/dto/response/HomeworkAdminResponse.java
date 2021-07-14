package com.dsmupgrade.domain.homework.dto.response;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.HomeworkFile;
import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class HomeworkAdminResponse {
    private int id;
    private String username;
    private String name;
    private String title;
    private LocalDateTime createdAt;
    private String status;
    private List<String> files;

    public static HomeworkAdminResponse from(PersonalHomework personalHomework){
        Homework homework = personalHomework.getHomework();
        List<HomeworkFile> files = personalHomework.getHomeworkFile();
        List<String> resultFiles = null;
        if(!CollectionUtils.isEmpty(files)){
            resultFiles = files.stream().map(
                    homeworkFile-> homeworkFile.getId().getName()
            ).collect(Collectors.toList());
        }
        return HomeworkAdminResponse.builder()
                .id(homework.getId())
                .username(personalHomework.getId().getStudentUsername())
                .name(personalHomework.getStudent().getName())
                .title(homework.getTitle())
                .createdAt(homework.getCreatedAt())
                .status(personalHomework.getStatus().name())
                .files(resultFiles)
                .build();
    }
}
