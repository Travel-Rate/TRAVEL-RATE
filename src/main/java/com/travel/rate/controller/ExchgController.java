package com.travel.rate.controller;

import com.travel.rate.dto.req.ReqTargetRateDTO;
import com.travel.rate.dto.res.ResExchgDTO;
import com.travel.rate.service.ExchgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("exchange-rate/*")
public class ExchgController {
    private final ExchgService exchgService;

    // 환율 알림 설정
    @PostMapping("target/add")
    public ResponseEntity<String> setTargetRateAdd(@RequestBody ReqTargetRateDTO reqTargetRateDTO){
        try{
            exchgService.setTargetRateAdd(reqTargetRateDTO);
            return ResponseEntity.ok("목표 환율 설정을 완료했습니다.");
        }catch (IllegalArgumentException e){
            return ResponseEntity.ok("잘못 입력됐습니다. 다시 설정 해주세요");
        }
    }

    // 환율 알림 삭제
    @DeleteMapping("target/{targetId}")
    public ResponseEntity<String> setTargetRateDelete(@PathVariable("targetId") Long targetId){
        try{
            exchgService.setTargetRateDelete(targetId);
            return ResponseEntity.ok("환율 알림 삭제를 완료했습니다.");
        }catch (IllegalArgumentException e){
            return ResponseEntity.ok("환율 알림 삭제중 오류가 생겼습니다.");
        }
    }

    // 환율 정보 API 목록
    @GetMapping("list")
    public List<ResExchgDTO> getExchgList() {
        List<ResExchgDTO> resExchgDTOS = exchgService.getExchgList();
        return resExchgDTOS;
    }

}
