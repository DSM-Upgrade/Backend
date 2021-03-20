package com.dsmupgrade.domain.homework.controller;

import com.dsmupgrade.domain.fine.dto.request.CompletionFineRequest;
import com.dsmupgrade.domain.homework.domain.*;
import com.dsmupgrade.domain.homework.dto.request.AssignmentHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ChangeHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.CompletionHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ReturnHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.response.UserAllHomeworkListResponse;
import com.dsmupgrade.domain.homework.dto.response.UserHomeworkResponse;
import com.dsmupgrade.domain.homework.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("homework")
@RestController
public class HomeworkController {
    private HomeworkService homeworkService;

    @GetMapping("list/{userName}")
    public List<UserAllHomeworkListResponse> getHomeworkList(@PathVariable("userName") String username){ // 유저마다 할당된 숙제의 리스트를 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        return homeworkService.getHomeworkList(username);
    }

    @GetMapping("content/{userName}/{homeworkId}")
    public UserHomeworkResponse getUserHomework(@PathVariable("userName") String username, @PathVariable("homeworkId") int homeworkId){ // 유저마다 할당된 숙제의 내용을 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        return homeworkService.getUserHomework(username, homeworkId);
    }

    @PostMapping("assignment")
    public void assignmentHomework(@RequestBody AssignmentHomeworkRequest assignmentHomeworkRequest){ // 유저에게 숙제 할당
        homeworkService.assignmentHomework(assignmentHomeworkRequest);
    }

    @PostMapping("return")
    public void returnHomework(@RequestBody ReturnHomeworkRequest returnHomeworkRequest){ // 숙제 반환
        homeworkService.returnHomework(returnHomeworkRequest);
    }

    @PostMapping("completion")
    public void completionHomework(@RequestBody CompletionHomeworkRequest completionHomeworkRequest){ // 숙제 완료
        homeworkService.completionHomework(completionHomeworkRequest);
    }

    @PatchMapping("change")
    public void changeHomework(@RequestBody ChangeHomeworkRequest changeHomeworkRequest){ // 할당한 숙제의 내용을 변경
        homeworkService.changeHomework(changeHomeworkRequest);
    }

    @DeleteMapping("elimination/{homeworkId}")
    public void deleteHomework(@PathVariable("homeworkId") Integer homeworkId){ // 숙제를 삭제
        homeworkService.deleteHomework(homeworkId);
    }
}
