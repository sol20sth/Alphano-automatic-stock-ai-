package com.ssafy.alphano.common.error.exception;

import com.ssafy.alphano.common.error.ErrorCode;

public class NicknameIsFoundException extends ServiceRuntimeException{
    public NicknameIsFoundException(ErrorCode errorCode){
        super(errorCode);
    }

    public ErrorCode getErrorCode(){
        return super.getErrorCode();
    }
}
