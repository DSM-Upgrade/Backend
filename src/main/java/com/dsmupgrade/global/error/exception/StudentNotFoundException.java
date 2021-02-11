package com.dsmupgrade.global.error.exception;

import com.dsmupgrade.global.error.exception.BusinessException;
import com.dsmupgrade.global.error.exception.ErrorCode;

public class StudentNotFoundException extends BusinessException {

    public StudentNotFoundException(String username) {
        super("username " + username + " is not found", ErrorCode.STUDENT_NOT_FOUND);
    }
}
