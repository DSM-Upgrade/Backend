package com.dsmupgrade.global.error.exception;

public class StudentNotFoundException extends BusinessException {

    public StudentNotFoundException() {
        super("user is not found", ErrorCode.STUDENT_NOT_FOUND);
    }

    public StudentNotFoundException(String username) {
        super("username " + username + " is not found", ErrorCode.STUDENT_NOT_FOUND);
    }
}
