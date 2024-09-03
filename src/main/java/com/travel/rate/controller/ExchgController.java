package com.travel.rate.controller;

import com.travel.rate.dto.req.ReqTargetRateDTO;
import com.travel.rate.dto.res.*;
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
    public ResApiResultDTO<String> setTargetRateUpdate(@PathVariable Long tagId, @RequestBody ReqTargetRateDTO reqTargetRateDTO) {
        exchgService.setTargetRateUpdate(tagId, reqTargetRateDTO);
        return ResApiResultDTO.success(ResponseCode.TARGET_UPDATE_SUCCESS.getMessage(),null);
    }

    // 목표환율 상세 조회
    @GetMapping("target/{tagId}")
    public ResApiResultDTO<ResTargetRateDTO> getTargetDetail(@PathVariable("tagId") Long tagId){
        ResTargetRateDTO resTargetRateDTO = exchgService.getTargetDetail(tagId);
        return ResApiResultDTO.dataOk(resTargetRateDTO, ResponseCode.TARGET_READ_SUCCESS.getMessage());
    }

    // 사용자 목표환율 목록 조회
    @GetMapping("target/list/{memId}")
    public ResApiResultDTO<List<ResTargetRateDTO>> getMemberTargetRateList(@PathVariable("memId") Long memId){
        List<ResTargetRateDTO> resTargetRateDTOS = exchgService.getMemberTargetRateList(memId);
        return ResApiResultDTO.dataOk(resTargetRateDTOS,ResponseCode.TARGET_READ_SUCCESS.getMessage());
    }

    // 통화 목록 조회
    @GetMapping("currencies")
    public List<ResCountryDTO> getCurrencyList(){
        return exchgService.getCurrencyList();
    }

    // 환율 알림 설정
    @PostMapping("target/add")
    public ResApiResultDTO<Object> setTargetRateAdd(@RequestBody ReqTargetRateDTO reqTargetRateDTO){
        exchgService.setTargetRateAdd(reqTargetRateDTO);
        return ResApiResultDTO.success(ResponseCode.TARGET_CREATE_SUCCESS.getMessage(),null);
    }

    // 환율 알림 삭제
    @DeleteMapping("target/{tagId}")
    public ResApiResultDTO<String> setTargetRateDelete(@PathVariable("tagId") Long tagId){
        exchgService.setTargetRateDelete(tagId);
        return ResApiResultDTO.success(ResponseCode.TARGET_DELETE_SUCCESS.getMessage(),null);

    }

    // 환율 정보 API 목록
    @GetMapping("list")
    public ResApiResultDTO<List<ResExchgDTO>> getExchgList() {
        List<ResExchgDTO> resExchgDTOS = exchgService.getExchgList();
        return ResApiResultDTO.dataOk(resExchgDTOS,ResponseCode.TARGET_READ_SUCCESS.getMessage());
    }

}
