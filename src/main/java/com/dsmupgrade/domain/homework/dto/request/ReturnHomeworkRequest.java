package com.dsmupgrade.domain.homework.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnHomeworkRequest {
    @NotNull
    private Integer homeworkId;
    @NotNull
    private String homeworkContent;

    private List<MultipartFile> personalHomeworkFile;
}