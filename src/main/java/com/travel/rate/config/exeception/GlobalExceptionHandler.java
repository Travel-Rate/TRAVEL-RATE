package com.travel.rate.config.exeception;

import com.travel.rate.dto.res.ErrorCode;
import com.travel.rate.dto.res.ResApiExceptionEntity;
import com.travel.rate.dto.res.ResApiResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessExceptionHandler.class)
    public ResponseEntity<ResApiResultDTO> handleCustomException(BusinessExceptionHandler e) {
        ResApiExceptionEntity resApiExceptionEntity = ResApiExceptionEntity.builder()
                .status(ErrorCode.BUSINESS_EXCEPTION_ERROR.getStatus())
                .errorMessage(ErrorCode.BUSINESS_EXCEPTION_ERROR.getMessage())
                .build();
        ResApiResultDTO resApiResultDTO = new ResApiResultDTO();
            resApiResultDTO.setStatus(400);
            resApiResultDTO.setMessage("");
            resApiResultDTO.setData(resApiExceptionEntity);
        return ResponseEntity.status(ErrorCode.BUSINESS_EXCEPTION_ERROR.getStatus())
                .body(resApiResultDTO);
    }

}
