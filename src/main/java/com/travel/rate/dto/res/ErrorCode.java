package com.travel.rate.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public enum ErrorCode {

    BUSINESS_EXCEPTION_ERROR(400),

    // Transaction Insert Error
    INSERT_ERROR(400),

    // Transaction Update Error
    UPDATE_ERROR(400),

    // Transaction Delete Error
    DELETE_ERROR(400),

    ; // End

    // 에러 코드의 '상태'을 반환한다.
    private int status;

    // 에러 코드의 '메시지'을 반환한다.
    private String message;

    ErrorCode(final int status) {
        this.status = status;
    }

    // 생성자 구성
    ErrorCode(final int status,final String message) {
        this.status = status;
        this.message = message;
    }

}
