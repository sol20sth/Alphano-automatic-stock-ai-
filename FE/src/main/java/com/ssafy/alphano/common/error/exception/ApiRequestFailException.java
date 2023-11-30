package com.ssafy.alphano.common.error.exception;

import com.ssafy.alphano.common.error.ErrorCode;

public class ApiRequestFailException extends ServiceRuntimeException{
    public ApiRequestFailException(ErrorCode errorCode){
        super(errorCode);
    }

    public ErrorCode getErrorCode(){

        return super.getErrorCode();

    }

}
