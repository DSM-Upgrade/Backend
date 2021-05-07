package com.dsmupgrade.global.error.exception;

public class FineNotFoundException extends BusinessException {

    public FineNotFoundException(int fineId) {
        super("fineId " + fineId + " is not registered", ErrorCode.FINE_NOT_FOUND);
    }
}
