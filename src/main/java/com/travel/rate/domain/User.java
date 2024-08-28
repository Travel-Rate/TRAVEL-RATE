package com.travel.rate.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {
//    user 엔티티

    @Id
    private String email;

//    ----------------------------------- 기준선

}
