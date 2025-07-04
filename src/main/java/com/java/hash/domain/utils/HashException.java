package com.java.hash.domain.utils;

import com.java.hash.domain.Enums.EnumErrorCode;

/**
 *
 * @author Jhonatan
 */
public class HashException extends RuntimeException {

    private String errorCode = "-1";

    public HashException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public HashException(EnumErrorCode error) {
        super(error.getError());
        this.errorCode = error.getKey();
    }

    public HashException(EnumErrorCode error, Object... args) {
        super(StringUtil.stringPatternFormat(error.getError(), args));
        this.errorCode = error.getKey();
    }

    public String getErrorCode() {
        return errorCode;
    }

}