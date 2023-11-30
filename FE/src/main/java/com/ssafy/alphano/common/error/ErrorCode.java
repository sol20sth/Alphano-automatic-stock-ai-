package com.ssafy.alphano.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "C001", "올바르지 않은 입력 입니다."),
    ENTITY_NOT_FOUND(400, "C003", "해당 엔티티를 찾지 못했습니다."),
    DUPLICATE_USER_NAME(401, "C004", "중복된 유저 아이디입니다."),
    API_REQUEST_FAIL(500, "C005", "API 요청에 실패했습니다."),
    INVALID_TOKEN(401, "C006", "유효하지 않은 토큰입니다."),
    STOCK_IS_FOUND(400, "C007", "이미 등록된 주문 옵션입니다."),
    NICKNAME_IS_FOUND(400, "C008", "이미 등록된 닉네임입니다.");

    private final int status;
    private final String code;
    private final String message;
}
