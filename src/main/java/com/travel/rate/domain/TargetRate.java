package com.travel.rate.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "target_rate")
public class TargetRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private float chgRate;

    private boolean rateRange;

    private Integer count;

    private boolean state;

    @ManyToOne
    @JoinColumn(name = "ctr_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "mem_id")
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


