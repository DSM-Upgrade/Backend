package com.dsmupgrade.domain.student.service;

import com.dsmupgrade.domain.student.dto.request.PasswordRequest;
import com.dsmupgrade.domain.student.dto.request.UpdateStudentRequest;
import com.dsmupgrade.domain.student.dto.response.StudentResponse;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService {

    StudentResponse getStudentByUsername(String username);

    void updateStudentPassword(String username, PasswordRequest passwordRequest);

    void updateStudentInfo(String username, UpdateStudentRequest updateStudentRequest);

    String updateStudentProfile(String username, MultipartFile file);
}
