package com.travel.rate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
// AccessLevel.PROTECTED : 접근권한 최소화 (세팅한 값만 사용하기 위해)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // 빌더패턴 : 가독성이 높고 일관성과 불변성을 지켜줌
    @Builder
    public Member(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
