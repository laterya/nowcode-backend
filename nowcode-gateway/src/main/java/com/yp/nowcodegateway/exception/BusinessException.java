package com.yp.nowcodegateway.exception;


import com.yp.nowapisdk.exception.ErrorCode;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4593480471566176059L;
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
