package com.dsmupgrade.controller;

import com.dsmupgrade.domain.entity.Fine;
import com.dsmupgrade.domain.entity.Homework;
import com.dsmupgrade.domain.entity.Personal_homework;
import com.dsmupgrade.domain.repository.FineRepository;
import com.dsmupgrade.domain.repository.HomeworkRepository;
import com.dsmupgrade.domain.repository.Personal_homeworkPk;
import com.dsmupgrade.domain.repository.Personal_homeworkRepository;
import com.dsmupgrade.dto.response.UserAllHomeworkListResponse;
import com.dsmupgrade.dto.response.UserHomeworkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("homework")
@RestController
public class HomeworkController {
    @Autowired
    private Personal_homeworkRepository personal_homeworkRepository;
    @Autowired
    private HomeworkRepository homeworkRepository;
    private SimpleDateFormat time_format;

    public HomeworkController() {
        time_format = new SimpleDateFormat("yyyy-MM-dd");
    }

    @GetMapping("list/{userName}")
    public List<UserAllHomeworkListResponse> getHomeworkList(@PathVariable("userName") String username){ // 유저마다 할당된 숙제의 리스트를 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        List<Personal_homework> allPersonalHomeworkList =  personal_homeworkRepository.findAllByStudentUsername(username);
        List<UserAllHomeworkListResponse> userAllHomeworkListResponse = new ArrayList<>();
        for(int i=0; i<allPersonalHomeworkList.size(); i++){
            UserAllHomeworkListResponse homework = new UserAllHomeworkListResponse();
            Optional<Homework> HomeworkContent = homeworkRepository.findById(allPersonalHomeworkList.get(i).getHomeworkId());
            homework.setHomeworkId(allPersonalHomeworkList.get(i).getHomeworkId());
            homework.setHomeworkTitle(HomeworkContent.get().getTitle());
            homework.setHomeworkStart(time_format.format(HomeworkContent.get().getCreatedAt()));
            homework.setHomeworkEnd(time_format.format(HomeworkContent.get().getDeadline()));
            homework.setHomeworkSubmitted((HomeworkContent.get().getCreatedAt()==null)?false:true);
            homework.setHomeworkFinished(allPersonalHomeworkList.get(i).getStatus());
            userAllHomeworkListResponse.add(homework);
        }
        return userAllHomeworkListResponse;
    }
    @GetMapping("content/{userName}/{homeworkId}")
    public UserHomeworkResponse getUserHomework(@PathVariable("userName") String username, @PathVariable("homeworkId") int homeworkId){ // 유저마다 할당된 숙제의 리스트를 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        Personal_homeworkPk personalHomeworkPk = new Personal_homeworkPk();
        personalHomeworkPk.setHomeworkId(homeworkId);
        personalHomeworkPk.setStudentUsername(username);
        Optional<Personal_homework> personalHomeworkContent =  personal_homeworkRepository.findById(personalHomeworkPk);
        Optional<Homework> homeworkContent = homeworkRepository.findById(homeworkId);
        UserHomeworkResponse userHomeworkResponse = new UserHomeworkResponse();
        userHomeworkResponse.setHomeworkTitle(homeworkContent.get().getTitle());
        userHomeworkResponse.setHomeworkStart(time_format.format(homeworkContent.get().getCreatedAt()));
        userHomeworkResponse.setHomeworkEnd(time_format.format(homeworkContent.get().getDeadline()));
        userHomeworkResponse.setHomeworkSubmittedDate(time_format.format(homeworkContent.get().getDeadline()));
        userHomeworkResponse.setHomeworkContent(homeworkContent.get().getContent());
        userHomeworkResponse.setHomeworkReturn(personalHomeworkContent.get().getContent());
        return userHomeworkResponse;
    }

}
