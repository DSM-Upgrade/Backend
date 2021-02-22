package com.dsmupgrade.service.authority;

import com.dsmupgrade.dto.request.AdminAuthRequest;
import com.dsmupgrade.dto.response.ListStudentResponse;

import java.util.List;

public interface AuthorityService {
    void AuthorityHomework(AdminAuthRequest adminAuthRequest);

    void AuthorityFine(AdminAuthRequest adminAuthRequest);

    void AdminAuth(AdminAuthRequest adminAuthRequest);

    List<ListStudentResponse> listUser();

    List<ListStudentResponse> listAuth();
}
