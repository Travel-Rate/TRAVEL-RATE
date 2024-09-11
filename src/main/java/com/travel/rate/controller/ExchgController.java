package com.travel.rate.controller;

import com.travel.rate.dto.req.ReqTargetRateDTO;
import com.travel.rate.dto.res.*;
import com.travel.rate.service.ExchgService;
import com.travel.rate.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("exchange-rate/*")
public class ExchgController {
    private final ExchgService exchgService;
    private final JwtService jwtService;

    // 목표환율 수정
    @PreAuthorize("hasRole('USER') and (#ReqTargetRateDTO.memId.toString() == princial.username)")
    @PutMapping("target/{tagId}")
    public ResApiResultDTO<String> setTargetRateUpdate(@PathVariable Long tagId, @RequestBody ReqTargetRateDTO reqTargetRateDTO) {
        exchgService.setTargetRateUpdate(tagId, reqTargetRateDTO);
        return ResApiResultDTO.success(null, ResponseCode.TARGET_UPDATE_SUCCESS.getMessage());
    }

    // 목표환율 상세 조회
    @PreAuthorize("hasRole('USER')")
    @GetMapping("target/{tagId}")
    public ResApiResultDTO<ResTargetRateDTO> getTargetDetail(@PathVariable("tagId") Long tagId){
        ResTargetRateDTO resTargetRateDTO = exchgService.getTargetDetail(tagId);
        return ResApiResultDTO.dataOk(resTargetRateDTO, ResponseCode.TARGET_READ_SUCCESS.getMessage());
    }

    // 사용자 목표환율 목록 조회
    @PreAuthorize("hasRole('USER') and (#memId.toString() == princial.username)")
    @GetMapping("target/list/{memId}")
    public ResApiResultDTO<List<ResTargetRateDTO>> getMemberTargetRateList(@PathVariable("memId") Long memId){
        List<ResTargetRateDTO> resTargetRateDTOS = exchgService.getMemberTargetRateList(memId);
        return ResApiResultDTO.dataOk(resTargetRateDTOS, ResponseCode.TARGET_READ_SUCCESS.getMessage());
    }

    // 통화 목록 조회
    @GetMapping("currencies")
    public List<ResCountryDTO> getCurrencyList(){
        return exchgService.getCurrencyList();
    }

    // 환율 알림 설정
    @PreAuthorize("hasRole('USER') and (#ReqTargetRateDTO.memId.toString() == princial.username)")
    @PostMapping("target/add")
    public ResApiResultDTO<Object> setTargetRateAdd(@RequestBody ReqTargetRateDTO reqTargetRateDTO){
        exchgService.setTargetRateAdd(reqTargetRateDTO);
        return ResApiResultDTO.success(null, ResponseCode.TARGET_CREATE_SUCCESS.getMessage());
    }

    // 환율 알림 삭제
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("target/{tagId}")
    public ResApiResultDTO<String> setTargetRateDelete(@PathVariable("tagId") Long tagId){
        exchgService.setTargetRateDelete(tagId);
        return ResApiResultDTO.success(null ,ResponseCode.TARGET_DELETE_SUCCESS.getMessage());

    }

    // 환율 정보 API 목록
    @GetMapping("list")
    public ResApiResultDTO<List<ResExchgDTO>> getExchgList() {
        List<ResExchgDTO> resExchgDTOS = exchgService.getExchgList();
        return ResApiResultDTO.dataOk(resExchgDTOS,ResponseCode.TARGET_READ_SUCCESS.getMessage());
    }

}
