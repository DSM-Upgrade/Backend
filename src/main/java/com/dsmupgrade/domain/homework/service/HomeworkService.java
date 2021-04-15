package com.dsmupgrade.domain.homework.service;

import com.dsmupgrade.domain.homework.dto.request.AssignmentHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ChangeHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.CompletionHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ReturnHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.response.UserAllHomeworkListResponse;
import com.dsmupgrade.domain.homework.dto.response.UserHomeworkResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomeworkService {
    List<UserAllHomeworkListResponse> getHomeworkList(String username);
    UserHomeworkResponse getUserHomework (String username, int homeworkId);
    void assignmentHomework(String requestUser, AssignmentHomeworkRequest assignmentHomeworkRequest);
    void returnHomework(String requestUser, ReturnHomeworkRequest returnHomeworkRequest);
    void completionHomework(CompletionHomeworkRequest completionHomeworkRequest);
    void changeHomework(ChangeHomeworkRequest changeHomeworkRequest);
    void deleteHomework(Integer homeworkId);
}
