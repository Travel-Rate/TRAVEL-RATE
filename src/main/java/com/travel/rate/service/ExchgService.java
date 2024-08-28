package com.travel.rate.service;

import com.travel.rate.dto.exchange.ResExchgDTO;
import com.travel.rate.utils.ExchangeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchgService {
    private final ExchangeUtils exchangeUtils;

    public List<ResExchgDTO> getExchgList(){
        List<ResExchgDTO> resExchgDTOS = exchangeUtils.getExchangeDataAsDtoList();
        return resExchgDTOS;
    }

}
