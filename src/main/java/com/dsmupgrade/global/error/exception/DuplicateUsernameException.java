package com.dsmupgrade.global.error.exception;

public class DuplicateUsernameException extends BusinessException {

    public DuplicateUsernameException() {
        super(ErrorCode.DUPLICATE_USERNAME);
    }
}
