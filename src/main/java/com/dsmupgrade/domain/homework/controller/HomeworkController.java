package com.dsmupgrade.domain.homework.controller;

import com.dsmupgrade.domain.homework.dto.request.AssignmentHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ChangeHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.CompletionHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.request.ReturnHomeworkRequest;
import com.dsmupgrade.domain.homework.dto.response.UserAllHomeworkListResponse;
import com.dsmupgrade.domain.homework.dto.response.UserHomeworkResponse;
import com.dsmupgrade.domain.homework.service.HomeworkService;
import com.dsmupgrade.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/homework")
@RequiredArgsConstructor
@RestController
public class HomeworkController {
    private final HomeworkService homeworkService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/list")
    public List<UserAllHomeworkListResponse> getHomeworkList(){ // 유저마다 할당된 숙제의 리스트를 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        return homeworkService.getHomeworkList(authenticationFacade.getUsername());
    }

    @GetMapping("/content/{homeworkId}")
    public UserHomeworkResponse getUserHomework(@PathVariable("homeworkId") int homeworkId){ // 유저마다 할당된 숙제의 내용을 받아옴 (반환은 되었지만, 완료가 되지 않은 것도 포함)
        return homeworkService.getUserHomework(authenticationFacade.getUsername(), homeworkId);
    }

    @PostMapping("/assignment")
    public void assignmentHomework(@RequestBody @Valid AssignmentHomeworkRequest assignmentHomeworkRequest){ // 유저에게 숙제 할당
        homeworkService.assignmentHomework(authenticationFacade.getUsername(), assignmentHomeworkRequest);
    }

    @PostMapping("/return")
    public void returnHomework(@RequestBody @Valid ReturnHomeworkRequest returnHomeworkRequest){ // 숙제 반환
        homeworkService.returnHomework(authenticationFacade.getUsername(), returnHomeworkRequest);
    }

    @PostMapping("/completion")
    public void completionHomework(@RequestBody @Valid CompletionHomeworkRequest completionHomeworkRequest){ // 숙제 완료
        homeworkService.completionHomework(completionHomeworkRequest);
    }

    @PatchMapping("/change")
    public void changeHomework(@RequestBody @Valid ChangeHomeworkRequest changeHomeworkRequest){ // 할당한 숙제의 내용을 변경
        homeworkService.changeHomework(authenticationFacade.getUsername(), changeHomeworkRequest);
    }

    @DeleteMapping("/elimination/{homeworkId}")
    public void deleteHomework(@PathVariable("homeworkId") Integer homeworkId){ // 숙제를 삭제
        homeworkService.deleteHomework(homeworkId);
    }
}
