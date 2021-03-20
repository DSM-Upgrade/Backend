package com.dsmupgrade.global.error.exception;

public class HomeworkNotFoundException extends BusinessException {

    public HomeworkNotFoundException(int homeworkId) {
        super("homeworkId " + homeworkId + " is not registered", ErrorCode.HOMEWORK_NOT_FOUND);
    }
}
