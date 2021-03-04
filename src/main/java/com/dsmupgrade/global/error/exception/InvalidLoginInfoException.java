package com.dsmupgrade.global.error.exception;

public class InvalidLoginInfoException extends BusinessException {

    public InvalidLoginInfoException() {
        super(ErrorCode.INVALID_LOGIN_INFO);
    }
}
