package com.dsmupgrade.domain.homework.service;

import org.springframework.stereotype.Service;

@Service
public interface CheckHomeworkService {
    void checkTimeOut(String username);
    void checkPersonalHomework(int id, String username);
}
