package com.ssafy.alphano.common.error.exception;

import com.ssafy.alphano.common.error.ErrorCode;

public class HttpClientErrorException extends ServiceRuntimeException{

    public HttpClientErrorException(ErrorCode errorCode){
        super(errorCode);
    }

    public ErrorCode getErrorCode(){
        return super.getErrorCode();
    }
}
