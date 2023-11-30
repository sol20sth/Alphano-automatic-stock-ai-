package com.ssafy.alphano.common.error;

import com.ssafy.alphano.common.error.exception.*;
import com.ssafy.alphano.common.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResponseErrorHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleException(Exception e) {
        log.error("Exception", e);
        final ApiError apiError = ApiError.of(ErrorCode.ENTITY_NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiRequestFailException.class)
    protected ResponseEntity<ApiError> handleApiRequestFailException(Exception e) {
        log.error("Exception", e);
        final ApiError apiError = ApiError.of(ErrorCode.API_REQUEST_FAIL);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ApiError> handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("Exception", e);
        final ApiError apiError = ApiError.of(ErrorCode.API_REQUEST_FAIL);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ApiError> handleAuthenticationException(HttpClientErrorException e) {
        log.error("Exception", e);
        final ApiError apiError = ApiError.of(ErrorCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StockIsFoundException.class)
    protected ResponseEntity<ApiError> handleStockIsFoundException(Exception e) {
        log.error("Exception", e);
        final ApiError apiError = ApiError.of(ErrorCode.STOCK_IS_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NicknameIsFoundException.class)
    protected ResponseEntity<ApiError> handleNicknameIsFoundException(Exception e) {
        log.error("Exception", e);
        final ApiError apiError = ApiError.of(ErrorCode.NICKNAME_IS_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
