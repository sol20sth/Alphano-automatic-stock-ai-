package com.ssafy.alphano.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseEntity<T> {

    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    public ResponseEntity(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<T> of(HttpStatus status, String message, T data) {
        return new ResponseEntity<>(status, message, data);
    }

    public static <T> ResponseEntity<T> of(HttpStatus status, T data ) {
        return of(status, status.name(), data);
    }

    public static <T> ResponseEntity<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }
}
