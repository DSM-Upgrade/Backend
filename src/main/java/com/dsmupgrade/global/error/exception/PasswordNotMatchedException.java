package com.dsmupgrade.global.error.exception;

public class PasswordNotMatchedException extends BusinessException {

    public PasswordNotMatchedException() {
        super(ErrorCode.PASSWORD_NOT_MATCHED);
    }
}
