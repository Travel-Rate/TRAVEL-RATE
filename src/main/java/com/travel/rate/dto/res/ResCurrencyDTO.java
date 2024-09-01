package com.travel.rate.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResCurrencyDTO {
    // 통화명, 통화코드 정보를 담는 DTO

    private Long curId;     // 통화PK
    private String code;    // 통화코드
    private String name;    // 국가명/통화명

    @Builder
    public ResCurrencyDTO(Long curId, String code, String name) {
        this.curId = curId;
        this.code = code;
        this.name = name;
    }
    
}
