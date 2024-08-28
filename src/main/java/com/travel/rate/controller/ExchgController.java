package com.travel.rate.controller;

import com.travel.rate.dto.exchange.ResExchgDTO;
import com.travel.rate.service.ExchgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ExchgController {
    private final ExchgService exchgService;

    // 환율 정보 목록
    @GetMapping("/exchange-rate/list")
    public List<ResExchgDTO> getExchgList() {
        List<ResExchgDTO> resExchgDTOS = exchgService.getExchgList();
        return resExchgDTOS;
    }

}
