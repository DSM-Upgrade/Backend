package com.dsmupgrade.domain.homework.controller;

import com.dsmupgrade.domain.homework.dto.request.HomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.PersonalHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.UserRequest;
import com.dsmupgrade.domain.homework.dto.response.HomeworkAdminResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentAdminResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkListResponse;
import com.dsmupgrade.domain.homework.service.HomeworkService;
import com.dsmupgrade.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/homework")
@RequiredArgsConstructor
@RestController
public class HomeworkController {
    private final HomeworkService homeworkService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping
    public List<HomeworkListResponse> getHomeworkList() {
        return homeworkService.getHomeworkList(authenticationFacade.getUsername());
    }

    @GetMapping("/{id}")
    public HomeworkContentResponse getHomeworkContent(@PathVariable("id") int id) {
        return homeworkService.getHomeworkContent(authenticationFacade.getUsername(), id);
    }

    @GetMapping("/all")
    public List<HomeworkAdminResponse> getHomeworkAllList() {
        return homeworkService.getHomeworkAllList();
    }

    @GetMapping("/admin/{user}/{id}")
    public List<HomeworkContentAdminResponse> getHomeworkContentAdmin(@PathVariable("user") String username, @PathVariable("id") int id) {
        return homeworkService.getHomeworkContentAdmin(username, id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void assignmentHomework(@Valid @RequestBody HomeworkRequest request) {
        homeworkService.assignmentHomework(request);
    }

    @PutMapping("/{id}/personal-homework")
    @ResponseStatus(HttpStatus.CREATED)
    public void submitHomework(@PathVariable("id") int id, @Valid @RequestPart(value = "files", required = false) List<MultipartFile> files,
                               @Valid @RequestBody PersonalHomeworkRequest request) {
        System.out.println("여기" + request.getContent() + files);
        homeworkService.submitHomework(id, authenticationFacade.getUsername(), request.getContent(), files);
    }

    @PatchMapping("/{id}/personal-homework")
    public void resubmitHomework(@PathVariable("id") int id, @Valid @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                 @Valid @RequestBody PersonalHomeworkRequest request) {
        homeworkService.resubmitHomework(id, authenticationFacade.getUsername(), request.getContent(), files);
    }

    @PostMapping("/{id}/personal-homework")
    public void changeHomeworkStatus(@PathVariable("id") int id, @Valid @RequestBody UserRequest request) {
        homeworkService.changeHomeworkStatus(id, request);
    }

    @PatchMapping("/{id}")
    public void changeHomework(@PathVariable("id") int id, @Valid @RequestBody HomeworkRequest request) {
        homeworkService.changeHomework(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteHomework(@PathVariable("id") int id) {
        homeworkService.deleteHomework(id);
    }
}
