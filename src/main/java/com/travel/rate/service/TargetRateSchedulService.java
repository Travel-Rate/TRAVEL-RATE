package com.travel.rate.service;

import com.travel.rate.domain.TargetRate;
import com.travel.rate.repository.TargetRateRepository;
import com.travel.rate.utils.ExchangeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetRateSchedulService {
    private final ExchangeUtils exchangeUtils;
    private final TargetRateRepository targetRateRepository;


    // @Scheduled(cron = "0 */5 11-19 * * ?")  // 매 5분마다, 오전 11시부터 오후 7시 55분까지 실행
//    @Scheduled(cron = "0/10 * * * * *") // 10초
    public void targetRateEmail(){
        LocalTime currentTime = LocalTime.now();
        LocalTime start = LocalTime.of(11, 0);
        LocalTime end = LocalTime.of(20, 0);

//        if(currentTime.isAfter(start) && currentTime.isBefore(end)){
            System.out.println("스케줄러 시작");
//            List<ResExchgDTO> resExchgDTOS = exchangeUtils.getExchangeDataAsDtoList();
            List<TargetRate> targetRates = targetRateRepository.findAll();
            for(TargetRate targetRate : targetRates){
                System.out.println("환율설정 목표환율 : " + targetRate.getChgRate());
                System.out.println("환율설정 목표환율 : " + targetRate.getRateRange());
                System.out.println("환율설정 통화코드 : " + targetRate.getCountry().getCurrency().getCode());
                System.out.println("환율설정 국가명/통화명 : " + targetRate.getCountry().getCurrency().getName());
                System.out.println("환율설정 회원 정보 : " + targetRate.getMember().getEmail());
//                for(ResExchgDTO resExchgDTO : resExchgDTOS){
//                    String curUnit = resExchgDTO.getCur_unit();   // 통화코드
//                    String curNm = resExchgDTO.getCur_nm();     // 국가명/통화명
//                    String dealBasR = resExchgDTO.getDeal_bas_r(); // 매매기준환율
//                    System.out.println("API 호출 정보 통화코드 : "+ curUnit);
//                    System.out.println("API 호출 정보 통화명 : "+ curNm);
//                }
//            }
        }

    }

}
