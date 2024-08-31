package com.travel.rate.dto.travel;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.travel.rate.domain.Country;
import com.travel.rate.domain.Currency;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class ResTravelDTO {
    double budget;
    Currency currency;// currency안에 country 존재?
    Country country;
}
