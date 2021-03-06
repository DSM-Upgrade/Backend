package com.dsmupgrade.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "C001", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "C002", "Invalid Input Value"),

    INVALID_TOKEN(401, "A001", "Invalid Token"),
    STUDENT_NOT_REGISTERED(403, "A002", "Student Not Registered"),
    INVALID_LOGIN_INFO(401, "A003", "Invalid Login Info"),
    EXPIRED_TOKEN(410, "A004", "Expired Token"),

    FILE_NOT_FOUND(404, "S004", "File Not Found"),
    FINE_NOT_FOUND(404, "S004", "Fine Not Found"),
    HOMEWORK_NOT_FOUND(404, "S004", "Homework Not Found"),
    STUDENT_NOT_FOUND(404, "S001", "Student Not Found"),
    PASSWORD_NOT_MATCHED(409, "S002", "Password Not Matched"),
    ADMIN_NOT_FOUND(404,"S003","Admin Not Found"),
    INVALID_FILE_TYPE(400, "S003", "Invalid File Type (should be image)"),
    DUPLICATE_USERNAME(409, "S004", "Duplicate Username"),

    FIELD_NOT_FOUND(404, "F001", "Field Not Found");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
