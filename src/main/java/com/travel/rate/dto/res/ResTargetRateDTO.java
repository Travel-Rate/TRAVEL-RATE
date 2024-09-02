package com.travel.rate.dto.res;

import com.travel.rate.domain.Country;
import com.travel.rate.domain.Currency;
import com.travel.rate.domain.Member;
import com.travel.rate.domain.TargetRate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResTargetRateDTO {
    // 환율 정보를 담은 DTO

    private Long tagId;

    private float chgRate;

    private String ctrName;

    private String curCode;



    @Builder
    public ResTargetRateDTO(TargetRate targetRate, Currency currency, Country country) {
        this.tagId = targetRate.getTagId();
        this.chgRate = targetRate.getChgRate();
        this.ctrName = country.getName();
        this.curCode = currency.getCode();

    }
}
