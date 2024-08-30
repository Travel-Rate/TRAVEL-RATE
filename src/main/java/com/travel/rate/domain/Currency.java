package com.travel.rate.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
@Entity
public class Currency {
    @Id
    private Long curId;

    @NotNull
    private String code;

    @NotNull
    private String name;
}
