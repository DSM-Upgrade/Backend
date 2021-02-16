package com.dsmupgrade.global.error.exception;

public class InvalidInputValueException extends BusinessException {

    public InvalidInputValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
