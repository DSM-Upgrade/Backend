package com.dsmupgrade.global.error.exception;

public class StudentNotRegisteredException extends BusinessException {

    public StudentNotRegisteredException(String username) {
        super("username " + username + " is not registered", ErrorCode.STUDENT_NOT_REGISTERED);
    }
}
