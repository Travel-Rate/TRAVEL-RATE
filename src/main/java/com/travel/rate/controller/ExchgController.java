package com.travel.rate.controller;

import com.travel.rate.dto.req.ReqTargetRateDTO;
import com.travel.rate.dto.res.ResCountryDTO;
import com.travel.rate.dto.res.ResExchgDTO;
import com.travel.rate.dto.res.ResTargetRateDTO;
import com.travel.rate.service.EmailService;
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
    private final EmailService emailService;

    // 목표환율 수정
    @PutMapping("target/{tagId}")
    public ResponseEntity<String> setTargetRateUpdate(@PathVariable Long tagId, @RequestBody ReqTargetRateDTO reqTargetRateDTO) {
        System.out.println("디티오에 타겟아이디가? "+reqTargetRateDTO.getTagId());
        System.out.println("여기에 나와야해! 타겟아이디가? "+tagId);
        try {
            exchgService.setTargetRateUpdate(tagId, reqTargetRateDTO);
            return ResponseEntity.ok("목표환율 수정을 완료했습니다.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 목표환율 상세 조회
    @GetMapping("target/{tagId}")
    public ResTargetRateDTO getTargetDetail(@PathVariable("tagId") Long tagId){
        return exchgService.getTargetDetail(tagId);
    }

    // 사용자 목표환율 목록 조회
    @GetMapping("target/list/{memId}")
    public List<ResTargetRateDTO> getMemberTargetRateList(@PathVariable("memId") Long memId){
        return exchgService.getMemberTargetRateList(memId);
    }

    // 통화 목록 조회
    @GetMapping("currencies")
    public List<ResCountryDTO> getCurrencyList(){
        return exchgService.getCurrencyList();
    }

    // 환율 알림 설정
    @PostMapping("target/add")
    public ResponseEntity<String> setTargetRateAdd(@RequestBody ReqTargetRateDTO reqTargetRateDTO){
        try{
            exchgService.setTargetRateAdd(reqTargetRateDTO);
            return ResponseEntity.ok("목표 환율 설정을 완료했습니다.");
        }catch (Exception e){
            return ResponseEntity.ok("");
        }
    }

    // 환율 알림 삭제
    @DeleteMapping("target/{tagId}")
    public ResponseEntity<String> setTargetRateDelete(@PathVariable("tagId") Long tagId){
        try{
            exchgService.setTargetRateDelete(tagId);
            return ResponseEntity.ok("환율 알림 삭제를 완료했습니다.");
        }catch (IllegalArgumentException e){
            return ResponseEntity.ok("");
        }
    }

    // 환율 정보 API 목록
    @GetMapping("list")
    public List<ResExchgDTO> getExchgList() {
        List<ResExchgDTO> resExchgDTOS = exchgService.getExchgList();
        return resExchgDTOS;
    }

}
