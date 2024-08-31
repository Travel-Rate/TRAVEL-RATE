package com.travel.rate.service;

import com.travel.rate.domain.Country;
import com.travel.rate.domain.Currency;
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



    }

    public List sortedBySeasonAndFilteredByCtntId(List<Currency> currencies, Long ctnt_id){
        int currentMonth = LocalDate.now().getDayOfMonth();
        String currentSeason = "";

        if(currentMonth >=3 && currentMonth <= 5)currentSeason = "봄";
        else if(currentMonth <= 8) currentSeason = "여름";
        else if(currentMonth <= 11) currentSeason = "가을";
        else currentSeason = "겨울";

        for(Currency currency : currencies){
            currency.getCountries().stream()
                    .filter(country -> country.getContinent().getCtntId() == ctnt_id)
                    .sorted()//TODO sort 로직 구현
                    .collect(Collectors.toList());
        }


        return null;
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
