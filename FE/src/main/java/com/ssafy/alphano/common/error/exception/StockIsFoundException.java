package com.ssafy.alphano.common.error.exception;

import com.ssafy.alphano.common.error.ErrorCode;

public class StockIsFoundException extends ServiceRuntimeException {
    public StockIsFoundException(ErrorCode errorCode){
        super(errorCode);
    }

    public ErrorCode getErrorCode(){
        return super.getErrorCode();
    }

}
