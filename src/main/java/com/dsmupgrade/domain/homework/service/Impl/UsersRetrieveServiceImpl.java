package com.dsmupgrade.domain.homework.service.Impl;

import com.dsmupgrade.domain.homework.domain.Homework;
import com.dsmupgrade.domain.homework.domain.HomeworkRepository;
import com.dsmupgrade.domain.homework.service.UsersRetrieveService;
import com.dsmupgrade.global.error.exception.HomeworkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersRetrieveServiceImpl implements UsersRetrieveService {

    private final HomeworkRepository homeworkRepository;

    @Override
    public List<String> getOriginUsers(int id) {
        Homework homework = homeworkRepository.findById(id).orElseThrow(() -> new HomeworkNotFoundException(id));
        if (homework.getPersonalHomeworks().isEmpty()) {
            return homework.getPersonalHomeworks()
                    .stream().map((personalHomework) -> personalHomework.getId().getStudentUsername())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getDeleteUsers(List<String> users, List<String> originUsers) {
        List<String> deleteUsers = new ArrayList<String>();
        for (String value : originUsers) {
            if (!users.contains(value)) {
                deleteUsers.add(value);
            }
        }
        return deleteUsers;
    }

    @Override
    public List<String> getAddUsers(List<String> users, List<String> originUsers) {
        List<String> addUsers = new ArrayList<String>();
        for (String value : users) {
            if (!originUsers.contains(value)) {
                addUsers.add(value);
            }
        }
        return addUsers;
    }
}
