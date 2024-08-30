package com.travel.rate.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "target_rate")
public class TargetRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private float chgRate;

    private boolean rateRange;

    private boolean count;

    private boolean state;

    @ManyToOne
    @JoinColumn(name = "ctr_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "mem_id")
    private Member member;

}


