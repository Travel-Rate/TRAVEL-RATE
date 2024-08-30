package com.travel.rate.dto.req;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqTargetRateDTO {
//    목표 환율 설정 정보를 받는 DTO
    private Long num;                 // 환율 알림 번호
    private String memberEmail;       // 사용자이메일
    private Long countryNum;          // 나라번호
    private String currencyNum;       // 통화번호
    private float chgRate;            // 목표환율
    private boolean rateRange;        // 목표환율범위 0:이하/1:이상
    private boolean count;            // 환율알림설정횟수 기본값:1/최대 3까지 가능
    private boolean state;            // 알림상태 0:미알림/1:알림
}
