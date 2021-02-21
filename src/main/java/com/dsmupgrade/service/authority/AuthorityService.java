package com.dsmupgrade.service.authority;

import com.dsmupgrade.dto.request.AdminAuthRequest;

public interface AuthorityService {
    void AuthorityHomework(AdminAuthRequest adminAuthRequest);
    void AuthorityFine(AdminAuthRequest adminAuthRequest);
    void AdminAuth(AdminAuthRequest adminAuthRequest);

}
