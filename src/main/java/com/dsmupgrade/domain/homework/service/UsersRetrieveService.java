package com.dsmupgrade.domain.homework.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersRetrieveService {
    List<String> getOriginUsers(int id);
    List<String> getDeleteUsers(List<String> users, List<String> originUsers);
    List<String> getAddUsers(List<String> users, List<String> originUsers);
}
