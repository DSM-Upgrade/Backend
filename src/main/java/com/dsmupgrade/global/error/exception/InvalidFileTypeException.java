package com.dsmupgrade.global.error.exception;

public class InvalidFileTypeException extends BusinessException {

    public InvalidFileTypeException() {
        super(ErrorCode.INVALID_FILE_TYPE);
    }
}
