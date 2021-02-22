package com.dsmupgrade.service.authority;

import com.dsmupgrade.dto.request.AdminAuthRequest;
import com.dsmupgrade.dto.response.ListStudentResponse;
import com.dsmupgrade.dto.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
//    private final StudentRepository studentRepository;

    @Override
    public void AuthorityHomework(AdminAuthRequest adminAuthRequest) {
//        String username = AuthenticationFacade.getUsername();
//        Student student = studentRepository.findByUsername(username);
//        studentRepository.save(student.fineManager());
    }

    @Override
    public void AuthorityFine(AdminAuthRequest adminAuthRequest) {
//        String username = AuthenticationFacade.getUsername();
//        Student student = studentRepository.findByUsername(username);
//        studentRepository.save(student.noticeManage());
    }

    @Override
    public void AdminAuth(AdminAuthRequest adminAuthRequest) {
//        String username = AuthenticationFacade.getUsername();
//        Student student = studentRepository.findByUsername(username);
//        studentRepository.save(student.register());
    }

    @Override
    public List<ListStudentResponse> listUser() {
        List<ListStudentResponse> list = new ArrayList<>();
//        for (Student student : studentRepository.findByAll()) {
//            if (student.isRegistered) {
//                list.add(
//                        ListStudentResponse.of(student)
//                );
//            }
//        }
        return list;
    }

    @Override
    public List<ListStudentResponse> listAuth() {
        List<ListStudentResponse> list = new ArrayList<>();
//        for (Student student : studentRepository.findByAll()) {
//            if (!student.isRegistered) {
//                list.add(
//                        ListStudentResponse.of(student)
//                );
//            }
//        }
        return list;

    }
}
