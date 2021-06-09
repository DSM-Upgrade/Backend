package com.dsmupgrade.domain.homework.service;

import org.springframework.stereotype.Service;

@Service
public interface CheckFacade {
    void checkTimeOut(String username);
    void checkPersonalHomework(int id, String username);
}
