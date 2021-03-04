package com.dsmupgrade.global.error.exception;

public class StudentNotAdminException extends BusinessException{
    public StudentNotAdminException(String username) {
        super("username " + username + " is not found", ErrorCode.ADMIN_NOT_FOUND);
    }

}
