package com.travel.rate.controller;

import com.travel.rate.dto.req.ReqTargetRateDTO;
import com.travel.rate.dto.res.ResCurrencyDTO;
import com.travel.rate.dto.res.ResExchgDTO;
import com.travel.rate.service.EmailService;
import com.travel.rate.service.ExchgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("exchange-rate/*")
public class ExchgController {
    private final ExchgService exchgService;
    private final EmailService emailService;

    @PostMapping("target/list/{memId}")
    public void getMemberTargetRateList(@PathVariable("memId") Long memId){

    }

    // 통화 목록 조회
    @GetMapping("currencies")
    public List<ResCurrencyDTO> getCurrencyList(){
         List<ResCurrencyDTO> resCurrencyDTOS = exchgService.getCurrencyList();
        return resCurrencyDTOS;
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

    // 메일 발송
    @GetMapping("email")
    public ResponseEntity<String> sendEmail(){
        String email = "";
        Long memId = 1L;
        emailService.sendSimpleMail(memId, email);
        return  ResponseEntity.ok("이메일을 성공적으로 보냈습니다.");
    }

}
