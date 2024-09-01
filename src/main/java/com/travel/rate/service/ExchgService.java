package com.travel.rate.service;

import com.travel.rate.domain.Country;
import com.travel.rate.domain.Currency;
import com.travel.rate.domain.Member;
import com.travel.rate.domain.TargetRate;
import com.travel.rate.dto.req.ReqTargetRateDTO;
import com.travel.rate.dto.res.ResExchgDTO;
import com.travel.rate.repository.CountryRepository;
import com.travel.rate.repository.CurrencyRepository;
import com.travel.rate.repository.MemberRepository;
import com.travel.rate.repository.TargetRateRepository;
import com.travel.rate.utils.ExchangeUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchgService {
    private final ExchangeUtils exchangeUtils;
    private final TargetRateRepository targetRateRepository;
    private final MemberRepository memberRepository;
    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;

    // 환율 알림 설정
    @Transactional
    public void setTargetRateAdd(ReqTargetRateDTO reqTargetRateDTO){

        Member member = memberRepository.findByEmail(reqTargetRateDTO.getMemberEmail());
        Country country = countryRepository.findByCtrId(reqTargetRateDTO.getCtrId());
        // 알람생성횟수 1, 알림상태 미알림 false
        int count = 1;
        boolean state = false;
        if(member == null){
            throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
        }
        if(country == null){
            throw new IllegalArgumentException("잘못 입력된 나라입니다.");
        }
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
    @Transactional
    public void setTargetRateDelete(Long targetId){
        TargetRate targetRate = targetRateRepository.findByTagId(targetId);
        if(targetRate == null){
            throw new IllegalArgumentException("이미 삭제되었거나 없는 환율 설정입니다.");
        }
        targetRateRepository.delete(targetRate);
    }

    // 환율 정보 API 목록
    public List<ResExchgDTO> getExchgList(){
        List<ResExchgDTO> resExchgDTOS = exchangeUtils.getExchangeDataAsDtoList();
        return resExchgDTOS;
    }

    public Map<String, Double> getExchgMap(){
        return exchangeUtils.getExchgMap();
    }

}
