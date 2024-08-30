package com.travel.rate.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TargetRate {
    @Id
    private Long num;
    private float chgRate;
    private boolean rateRange;
    private Integer count;
    private boolean state;

    @ManyToOne
    @JoinColumn(name = "con_num")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cur_code")
    private Currency currency;

    @Builder
    public TargetRate(Long num, float chgRate, boolean rateRange,
                      Integer count, boolean state, Country country,
                      Member member, Currency currency) {
        this.num = num;
        this.chgRate = chgRate;
        this.rateRange = rateRange;
        this.count = count;
        this.state = state;
        this.country = country;
        this.member = member;
        this.currency = currency;

    }

}


