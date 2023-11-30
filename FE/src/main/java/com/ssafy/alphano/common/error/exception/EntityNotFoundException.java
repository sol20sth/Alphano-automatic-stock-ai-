package com.ssafy.alphano.common.error.exception;

import com.ssafy.alphano.common.error.ErrorCode;

public class EntityNotFoundException extends ServiceRuntimeException{


    public EntityNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }

    public ErrorCode getErrorCode(){
        return super.getErrorCode();
    }

}
