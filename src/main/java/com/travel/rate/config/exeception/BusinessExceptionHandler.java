package com.travel.rate.config.exeception;

import com.travel.rate.dto.res.ErrorCode;
import lombok.Builder;
import lombok.Getter;

public class BusinessExceptionHandler extends RuntimeException{
    @Getter
    private final ErrorCode errorCode;

    @Builder
    public BusinessExceptionHandler(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
