package com.dsmupgrade.domain.homework.service;

import com.dsmupgrade.domain.homework.dto.request.HomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.PersonalHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.UserRequest;
import com.dsmupgrade.domain.homework.dto.response.HomeworkContentResponse;
import com.dsmupgrade.domain.homework.dto.response.HomeworkListResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HomeworkService {
    List<HomeworkListResponse> getHomeworkList(String username);
    HomeworkContentResponse getHomeworkContent(String username, int id);
    void assignmentHomework(HomeworkRequest requset);
    void submitHomework(int id, String username, PersonalHomeworkRequest request);
    void resubmitHomework(int id, String username, PersonalHomeworkRequest request);
    void changeHomeworkStatus(int id, UserRequest request);
    void changeHomework(int id, HomeworkRequest request);
    void deleteHomework(int id);
}
