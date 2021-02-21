package com.dsmupgrade.service.authority;

import com.dsmupgrade.dto.request.AdminAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
