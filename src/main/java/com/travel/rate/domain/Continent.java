package com.travel.rate.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Continent {
    @Id
    private Long ctntId;

    @NotNull
    private String name;

}
