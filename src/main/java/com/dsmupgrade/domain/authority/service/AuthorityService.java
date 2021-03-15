package com.dsmupgrade.domain.authority.service;


import com.dsmupgrade.domain.authority.dto.request.AdminAuthRequest;
import com.dsmupgrade.domain.authority.dto.response.ListStudentResponse;

import java.util.List;

public interface AuthorityService {
    void authorityHomework(AdminAuthRequest adminAuthRequest);

    void authorityFine(AdminAuthRequest adminAuthRequest);

    void adminAuth(AdminAuthRequest adminAuthRequest);

    List<ListStudentResponse> listUser();

    List<ListStudentResponse> listAuth();
}
