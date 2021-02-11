package com.dsmupgrade.global.error.exception;

import com.dsmupgrade.global.error.exception.BusinessException;
import com.dsmupgrade.global.error.exception.ErrorCode;

public class FieldNotFoundException extends BusinessException {

    public FieldNotFoundException(int id) {
        super("field id " + id + " is not found", ErrorCode.FIELD_NOT_FOUND);
    }
}
