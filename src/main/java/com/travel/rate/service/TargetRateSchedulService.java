package com.travel.rate.service;

import com.travel.rate.domain.TargetRate;
import com.travel.rate.dto.res.ResExchgDTO;
import com.travel.rate.repository.TargetRateRepository;
import com.travel.rate.utils.ExchangeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TargetRateSchedulService {
    private final ExchangeUtils exchangeUtils;
    private final TargetRateRepository targetRateRepository;
    private final EmailService emailService;

    // @Scheduled(cron = "0 */5 11-19 * * ?")  // 매 5분마다, 오전 11시부터 오후 7시 55분까지 실행
    @Scheduled(cron = "0 */1 * * * ?") // 매일 1분마다
    public void targetRateEmail(){
        LocalDateTime now = LocalDateTime.now();
        LocalTime start = LocalTime.of(11, 0);
        LocalTime end = LocalTime.of(20, 0);

//        if (now.getDayOfWeek().getValue() >= DayOfWeek.MONDAY.getValue() &&
//                now.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue() &&
//                now.toLocalTime().isAfter(start) && now.toLocalTime().isBefore(end)) {
            System.out.println("스케줄러 시작");
            List<ResExchgDTO> resExchgDTOS = exchangeUtils.getExchangeDataAsDtoList();
            List<TargetRate> targetRates = targetRateRepository.findAll();
            String targetCode = "";         // 목표환율 통화코드
            float targetChgRate = 0;        // 목표환율
            String targetName = "";         // 목표환율 국가명/통화명
            String targetMemberName = "";   // 사용자 성명
            String targetMemberEmail = "";  // 사용자 메일
            String exchangeCode = "";       // API 통화코드
            String exchangeName = "";       // API 국가명/통화명
            String exchangeDeal = "";       // API 환율
            for(TargetRate targetRate : targetRates){
                targetCode = targetRate.getCountry().getCurrency().getCode();
                targetChgRate = targetRate.getChgRate();
                targetMemberEmail = targetRate.getMember().getEmail();
                targetMemberName = targetRate.getMember().getName();
                System.out.println("환율설정 목표환율 : " + targetRate.getChgRate());
                System.out.println("환율설정 통화코드 : " + targetRate.getCountry().getCurrency().getCode());
                System.out.println("환율설정 국가명/통화명 : " + targetRate.getCountry().getCurrency().getName());
                System.out.println("환율설정 회원 정보 : " + targetRate.getMember().getEmail());
                System.out.println("환율설정 회원 정보 : " + targetRate.getMember().getName());

                // 이중 포문을 나가기 위한 루프 설정
                targetLoop:
                for(ResExchgDTO resExchgDTO : resExchgDTOS){
                    exchangeCode = resExchgDTO.getCur_unit();
                    exchangeDeal = resExchgDTO.getDeal_bas_r();
                    System.out.println("통화코드    :" + resExchgDTO.getCur_unit());
                    System.out.println("국가/통화명 :" + resExchgDTO.getCur_nm());
                    System.out.println("매매 기준율 :" + resExchgDTO.getDeal_bas_r());
                    if(targetCode.equals(exchangeCode) && Float.toString(targetChgRate).equals(exchangeDeal) ){
                        System.out.println("----------------------------------둘의 값이 같으면 일로찍혀라!");
                        System.out.println("----------------내 목표 환율 설정 " + targetCode);
                        System.out.println("한국수출입 은행 코드으으으으으으으으으" + exchangeCode);
                        System.out.println("------내 목표 환율 설정" + targetChgRate);
                        System.out.println("한국 수출입 은행의 환유우우우우우우욹" + exchangeDeal);
                        System.out.println("이때 멤버 아이디는?!" + targetMemberEmail);
//                        emailService.sendSimpleMail(
//                                targetMemberEmail,
//                                targetMemberName,
//                                targetName,
//                                targetCode,
//                                Float.toString(targetChgRate)
//                        );
                        break targetLoop;
                    }
                }

            }

//        }

    }

}
