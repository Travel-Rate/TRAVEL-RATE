package com.travel.rate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Country {
    @Id
    private int num;// 1 2 3 4

    @NotNull
    private String name;// 나라명
    @NotNull
    private int cntNum;// 대륙코드 10 20 30
    @NotNull
    private String cntName;// 대륙명
    @NotNull
    private String season;// 추천계절
    @NotNull
    private boolean state;// 여행 가능 true, 금지 false

    @ManyToOne
    @JoinColumn(name="cur_code")
    private Currency currency;
}
