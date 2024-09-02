package com.travel.rate.service;

import com.travel.rate.exception.BusinessExceptionHandler;
import com.travel.rate.domain.Country;
import com.travel.rate.domain.Currency;
import com.travel.rate.domain.Member;
import com.travel.rate.domain.TargetRate;
import com.travel.rate.dto.req.ReqTargetRateDTO;
import com.travel.rate.dto.res.*;
import com.travel.rate.repository.CountryRepository;
import com.travel.rate.repository.CurrencyRepository;
import com.travel.rate.repository.MemberRepository;
import com.travel.rate.repository.TargetRateRepository;
import com.travel.rate.utils.ExchangeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchgService {
    private final ExchangeUtils exchangeUtils;
    private final TargetRateRepository targetRateRepository;
    private final MemberRepository memberRepository;
    private final CountryRepository countryRepository;

    // 목표환율 수정
    @Transactional
    public void setTargetRateUpdate(Long tagId, ReqTargetRateDTO reqTargetRateDTO) throws Exception {
        TargetRate targetRate = targetRateRepository.findById(tagId)
                .orElseThrow(()-> new IllegalArgumentException("목표환율을 찾을 수 없습니다."));
        boolean countryCheck = countryRepository.existsByCtrId(reqTargetRateDTO.getCtrId());
        if(!countryCheck){
            throw new IllegalArgumentException("해당 국가는 없습니다.");
        }
        System.out.println("업데이트 전 타겟레이트 정보 : "+targetRate.getChgRate());
        Country country = countryRepository.findByCtrId(reqTargetRateDTO.getCtrId());
        targetRate.update(
                reqTargetRateDTO.getChgRate(),
                reqTargetRateDTO.getRateRange(),
                country
            );
        System.out.println("업데이트 후우우우우 타겟레이트 정보 : "+targetRate.getChgRate());
        targetRateRepository.save(targetRate);

    }

    // 목표환율 상세 조회
    public ResTargetRateDTO getTargetDetail(Long tagId){
        TargetRate targetRate = targetRateRepository.findOneByTagId(tagId);
        ResTargetRateDTO resTargetRateDTO = ResTargetRateDTO.builder()
                .targetRate(targetRate)
                .country(targetRate.getCountry())
                .currency(targetRate.getCountry().getCurrency())
                .build();
        return resTargetRateDTO;
    }

    // 사용자 목표환율 목록 조회
    public List<ResTargetRateDTO> getMemberTargetRateList(Long memId){
        List<TargetRate> targetRates = targetRateRepository.getMemberTarget(memId);
        List<ResTargetRateDTO> resTargetRateDTOS = new ArrayList<>();
        for(TargetRate targetRate : targetRates){
            ResTargetRateDTO resTargetRateDTO = ResTargetRateDTO.builder()
                    .targetRate(targetRate)
                    .country(targetRate.getCountry())
                    .currency(targetRate.getCountry().getCurrency())
                    .build();
            resTargetRateDTOS.add(resTargetRateDTO);
        }
        return resTargetRateDTOS;
    }

    // 통화 목록 조회
    public List<ResCountryDTO> getCurrencyList(){

        List<Country> currencies = countryRepository.findAll();
        List<ResCountryDTO> resCountryDTOS = new ArrayList<>();
        for(Country country : currencies){
            ResCountryDTO resCurrencyDTO = ResCountryDTO.builder()
                    .ctrId(country.getCtrId())
                    .curId(country.getCurrency().getCurId())
                    .code(country.getCurrency().getCode())
                    .name(country.getName())
                    .build();
            resCountryDTOS.add(resCurrencyDTO);
        }

        return resCountryDTOS;
    }

    // 목표 환율 설정
    @Transactional
    public void setTargetRateAdd(ReqTargetRateDTO reqTargetRateDTO)throws Exception{
        boolean memberCheck= memberRepository.existsByMemId(reqTargetRateDTO.getMemId());
        boolean countryCheck = countryRepository.existsByCtrId(reqTargetRateDTO.getCtrId());
        List<TargetRate> targetRates = targetRateRepository.getMemberTarget(reqTargetRateDTO.getMemId());
        if(targetRates.size() > 3){
            throw new BusinessExceptionHandler("더이상 환율 알림 설정을 만드실 수 없습니다.", ErrorCode.BUSINESS_EXCEPTION_ERROR);
        }
        // 알람생성횟수 1, 알림상태 미알림 false
        int count = 1;
        boolean state = false;
        if(!memberCheck){
            throw new BusinessExceptionHandler("회원을 찾을 수 없습니다.", ErrorCode.BUSINESS_EXCEPTION_ERROR);
        }else if(!countryCheck){
            throw new BusinessExceptionHandler("나라를 찾을 수 없습니다.", ErrorCode.BUSINESS_EXCEPTION_ERROR);
        }else if(memberCheck && countryCheck){
            // 회원과 나라가 있을 때
            Member member = memberRepository.findByMemId(reqTargetRateDTO.getMemId());
            Country country = countryRepository.findByCtrId(reqTargetRateDTO.getCtrId());
            TargetRate targetRate = TargetRate.builder()
                    .tagId(reqTargetRateDTO.getTagId())
                    .member(member)
                    .country(country)
                    .chgRate(reqTargetRateDTO.getChgRate())
                    .count(count)
                    .state(state)
                    .build();
            targetRateRepository.save(targetRate);
        }

    }
    
    // 목표 환율 삭제
    @Transactional
    public void setTargetRateDelete(Long tagId){
        boolean targetRateCheck = targetRateRepository.existsByTagId(tagId);
        if(!targetRateCheck){
            throw new IllegalArgumentException("이미 삭제되었거나 없는 환율 설정입니다.");
        }
        targetRateRepository.deleteById(tagId);
    }

    // 환율 정보 API 목록
    public List<ResExchgDTO> getExchgList(){
        List<ResExchgDTO> resExchgDTOS = exchangeUtils.getExchangeDataAsDtoList();
        for(ResExchgDTO resExchgDTO : resExchgDTOS){
            System.out.println("결과       :" + resExchgDTO.getResult());
            System.out.println("통화코드    :" + resExchgDTO.getCur_unit());
            System.out.println("국가/통화명 :" + resExchgDTO.getCur_nm());
            System.out.println("송금받을때 :" + resExchgDTO.getTtb());
            System.out.println("송금보낼때 :" + resExchgDTO.getTts());
            System.out.println("매매 기준율 :" + resExchgDTO.getDeal_bas_r());
            System.out.println("장부가격 :" + resExchgDTO.getYy_efee_r());
            System.out.println("통화코드 :" + resExchgDTO.getCur_unit());
        }
        return resExchgDTOS;
    }

    public Map<String, Double> getExchgMap(){
        return exchangeUtils.getExchgMap();
    }

}
