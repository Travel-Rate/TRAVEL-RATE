package com.travel.rate.service;

import com.travel.rate.domain.Country;
import com.travel.rate.domain.Currency;
import com.travel.rate.dto.travel.ResTravelDTO;
import com.travel.rate.repository.CountryRepository;
import com.travel.rate.utils.ExchangeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TravelService {

    private final CountryRepository countryRepository;
    private final ExchgService exchgService;
    private final ExchangeUtils exchangeUtils;

    public void makeCountryRecommandation(Long ctnt_id){
        List<Country> countryList = countryRepository.findAllByCtntId(ctnt_id);

        List<Currency> top3Currencies = getTop3Currencies(countryList);

        top3Currencies = sortedBySeasonAndFilteredByCtntId(top3Currencies, ctnt_id);

        //TODO 정렬완성된 것에서 currency당 1등을 RES로 변환

    }

    public String getCurrentSeason(){
        int currentMonth = LocalDate.now().getDayOfMonth();
        String currentSeason = "";

        if(currentMonth >=3 && currentMonth <= 5)currentSeason = "봄";
        else if(currentMonth <= 8) currentSeason = "여름";
        else if(currentMonth <= 11) currentSeason = "가을";
        else currentSeason = "겨울";

        return currentSeason;
    }

    public List<Currency> sortedBySeasonAndFilteredByCtntId(List<Currency> currencies, Long ctntId){

        String currentSeason = getCurrentSeason();

        for(Currency currency : currencies) {
            String finalCurrentSeason = currentSeason;
            currency.getCountries().sort((Country country1, Country country2)-> {
                // 현재 계절일 경우 랜덤으로 점수를 받고 점수 '오름차순' 정렬
                int country1Point = 0;
                int country2Point = 0;

                if(country1.getSeason().matches(finalCurrentSeason)){
                    country1Point = new Random().nextInt(9)+1;
                }
                if(country1.getSeason().matches(finalCurrentSeason)){
                    country1Point = new Random().nextInt(9)+1;
                }

                // 선택했던 대륙이 아닐 경우 최대 벌점을 주어 꼴등
                if(country1.getContinent().getCtntId() != ctntId) country1Point = Integer.MAX_VALUE;
                if(country2.getContinent().getCtntId() != ctntId) country1Point = Integer.MAX_VALUE;

                return country1Point - country2Point;
            });
        }

        return currencies;
    }

    public List<Currency> getTop3Currencies(List<Country> countryList){
        // 나라에 해당하는 통화 뽑기
        Set<Currency> currencySet = new HashSet<>();
        for(Country country : countryList){
            currencySet.add(country.getCurrency());
        }

        // 수출입은행 환율 조회값 <통화코드, 환율>
        Map<String, Double> map = exchangeUtils.getExchgMap();

        // 통화 환율과 연결해서 환율 오름차순 정렬
        List<Currency> sortedCurrencies = new ArrayList<>(currencySet);
        sortedCurrencies.sort(new Comparator<Currency>() {
            @Override
            public int compare(Currency c1, Currency c2) {
                return (map.get(c1.getCode()) - map.get(c2.getCode()) )<0 ? -1 : 1;
            }
        });

        return sortedCurrencies.subList(0, 3);
    }



}
