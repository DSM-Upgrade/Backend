package com.dsmupgrade.domain.homework.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentHomeworkRequest {
    @NotNull
    private List<String> username;
    @NotNull
    private String homeworkTitle;
    @NotNull
    private String homeworkContent;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd`T`HH:mm:ss")
    private LocalDateTime deadline;

    private List<MultipartFile> homeworkFile;
}
