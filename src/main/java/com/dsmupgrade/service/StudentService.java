package com.dsmupgrade.service;

import com.dsmupgrade.dto.request.PasswordRequest;
import com.dsmupgrade.dto.response.StudentResponse;

public interface StudentService {

    StudentResponse getStudentByUsername(String username);

    void updateStudentPassword(String username, PasswordRequest passwordRequest);
}