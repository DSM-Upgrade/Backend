package com.dsmupgrade.global.error;

import com.dsmupgrade.global.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private final String message;
    private final int status;
    private final String code;

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getMessage(), errorCode.getStatus(), errorCode.getCode());
    }
}
