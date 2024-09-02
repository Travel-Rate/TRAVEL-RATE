package com.travel.rate.dto.res;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@ToString
public class ResApiResultDTO{
    //  상태코드, 메세지, 데이터 등 응답정보를 담는 DTO
    private int status;
    private String message;
    private ResApiExceptionEntity exception;
    private Object data;

    public ResApiResultDTO(int status, String message, ResApiExceptionEntity exception, Object data) {
        this.status = status;
        this.message = message;
        this.exception = exception;
        this.data = data;
    }

    public static ResApiResultDTO res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static ResApiResultDTO res(final int status, final String message, final Object obj) {
            ResApiResultDTO resApiResultDTO = new ResApiResultDTO(status, message, null, obj);
        return resApiResultDTO;
    }

}
