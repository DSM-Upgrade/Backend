package com.dsmupgrade.domain.homework.service.Impl;

import com.dsmupgrade.domain.homework.domain.PersonalHomework;
import com.dsmupgrade.domain.homework.domain.PersonalHomeworkPk;
import com.dsmupgrade.domain.homework.domain.PersonalHomeworkRepository;
import com.dsmupgrade.domain.homework.domain.PersonalHomeworkStatus;
import com.dsmupgrade.domain.homework.service.CheckHomeworkService;
import com.dsmupgrade.global.error.exception.HomeworkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckHomeworkServiceImpl implements CheckHomeworkService {

    private final PersonalHomeworkRepository personalHomeworkRepository;

    @Override
    public void checkTimeOut(String username) {
        List<PersonalHomework> personalHomeworkList = personalHomeworkRepository.findByIdStudentUsername(username);
        personalHomeworkList.forEach(
                (personalHomework) -> {
                    if (isTimeOver(personalHomework)) {
                        personalHomework.setStatus(PersonalHomeworkStatus.UNSUBMITTED);
                        personalHomeworkRepository.save(personalHomework);
                    }
                }
        );
    }

    private boolean isTimeOver(PersonalHomework personalHomework){
        return personalHomework.getHomework().getDeadline().isBefore(LocalDateTime.now())
                && personalHomework.getStatus() == PersonalHomeworkStatus.ASSIGNED;
    }

    @Override
    public void checkPersonalHomework(int id, String username) {
        personalHomeworkRepository.findById(new PersonalHomeworkPk(id, username))
                .orElseThrow(() -> new HomeworkNotFoundException(id, username));
    }
}
