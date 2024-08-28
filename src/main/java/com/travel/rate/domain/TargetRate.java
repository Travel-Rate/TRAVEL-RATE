package com.travel.rate.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
public class TargetRate {
    @Id
    private Long num;
    private float chgRate;
    private boolean rateRange;
    private boolean count;
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
}


