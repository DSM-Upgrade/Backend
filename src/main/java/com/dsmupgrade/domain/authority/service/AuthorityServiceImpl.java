package com.dsmupgrade.domain.authority.service;

import com.dsmupgrade.domain.authority.dto.request.AdminAuthRequest;
import com.dsmupgrade.domain.authority.dto.response.ListStudentResponse;
import com.dsmupgrade.domain.student.domain.Student;
import com.dsmupgrade.domain.student.domain.StudentRepository;
import com.dsmupgrade.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final StudentRepository studentRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public void authorityHomework(AdminAuthRequest adminAuthRequest) {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow();
        if (admin.getIsAdmin()) {
            Student student = studentRepository.findByUsername(adminAuthRequest.getUsername()).orElseThrow();
            student.noticeManager();
            studentRepository.save(student);
        }    }

    @Override
    public void authorityFine(AdminAuthRequest adminAuthRequest) {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow();
        if (admin.getIsAdmin()) {
            Student student = studentRepository.findByUsername(adminAuthRequest.getUsername()).orElseThrow();
//            student.fineManager();
            studentRepository.save(student);
        }    }

    @Override
    public void adminAuth(AdminAuthRequest adminAuthRequest) {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow();
        if (admin.getIsAdmin()) {
            Student student = studentRepository.findByUsername(adminAuthRequest.getUsername()).orElseThrow();
//            student.register();
            studentRepository.save(student);
        }
    }

    @Override
    public List<ListStudentResponse> listUser() {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow();
        List<ListStudentResponse> list = new ArrayList<>();
        if (admin.getIsAdmin()) {
//            for (Student student : studentRepository.findByAll()) {
//                if (student.getIsRegistered()) {
//                    list.add(
//                            ListStudentResponse.of(student)
//                    );
//                }
//            }
        }
        return list;
    }

    @Override
    public List<ListStudentResponse> listAuth() {
        String adminName = authenticationFacade.getUsername();
        Student admin = studentRepository.findByUsername(adminName).orElseThrow();
        List<ListStudentResponse> list = new ArrayList<>();
        if (admin.getIsAdmin()) {
//            for (Student student : studentRepository.findByAll()) {
//                if (!student.getIsRegistered()) {
//                    list.add(
//                            ListStudentResponse.of(student)
//                    );
//                }
//            }
        }
        return list;

    }
}
