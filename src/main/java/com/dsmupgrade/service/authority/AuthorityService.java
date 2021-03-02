package com.dsmupgrade.service.authority;


import com.dsmupgrade.dto.request.AdminAuthRequest;
import com.dsmupgrade.dto.response.ListStudentResponse;

import java.util.List;

public interface AuthorityService {
    void authorityHomework(AdminAuthRequest adminAuthRequest);

    void authorityFine(AdminAuthRequest adminAuthRequest);

    void adminAuth(AdminAuthRequest adminAuthRequest);

    List<ListStudentResponse> listUser();

    List<ListStudentResponse> listAuth();
}
