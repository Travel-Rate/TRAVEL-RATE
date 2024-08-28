package com.travel.rate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {
//    user 엔티티
    @Id
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "member")// 순환참조 막기
    private List<TargetRate> targetRateList;

//    ----------------------------------- 기준선

}
