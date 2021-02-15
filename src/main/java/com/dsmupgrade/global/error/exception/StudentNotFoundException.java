package com.dsmupgrade.global.error.exception;

public class StudentNotFoundException extends BusinessException {

    public StudentNotFoundException(String username) {
        super("username " + username + " is not found", ErrorCode.STUDENT_NOT_FOUND);
    }
}
