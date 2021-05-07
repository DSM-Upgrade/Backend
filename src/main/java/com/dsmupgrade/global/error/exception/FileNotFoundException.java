package com.dsmupgrade.global.error.exception;


public class FileNotFoundException extends BusinessException {

    public FileNotFoundException(String filename) {
        super("fineName " + filename + " is not existed", ErrorCode.FILE_NOT_FOUND);
    }
}
