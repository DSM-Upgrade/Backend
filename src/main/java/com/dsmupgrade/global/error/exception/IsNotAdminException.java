package com.dsmupgrade.global.error.exception;

public class IsNotAdminException extends BusinessException{
    public IsNotAdminException() {
        super(ErrorCode.PASSWORD_NOT_MATCHED);
    }

}
