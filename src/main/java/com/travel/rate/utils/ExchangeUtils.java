package com.travel.rate.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.rate.dto.exchange.ResExchgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ExchangeUtils {

//    API 인증키
    @Value("${exchange-authkey}")
    private String authkey;

    @Value("${exchange-data}")
    private String data;

    private final String serchdate = getSearchdate();

    // 한국 수출입은행 API호출
    public JsonNode getExchangeDataSync(){

        // 인코딩 모드 None 설정
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        // WebClient 생성
        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).build();
        String responseBody = webClient.get()
                .uri(builder -> builder
                        .scheme("https")
                        .host("www.koreaexim.go.kr")
                        .path("/site/program/financial/exchangeJSON")
                        .queryParam("authkey", authkey)
                        .queryParam("serchdate", serchdate)
                        .queryParam("data", data)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseJson(responseBody);
    }

    // getExchangeDataSync()에서 가저온 결과 값(String responseBody)을 Json 형식으로
    private JsonNode parseJson(String responseBody){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(responseBody);
        } catch (IOException e){
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }

    public List<ResExchgDTO> getExchangeDataAsDtoList() {
        JsonNode jsonNode = getExchangeDataSync();

        if (jsonNode != null && jsonNode.isArray()) {
            List<ResExchgDTO> resExchgDTOS = new ArrayList<>();

            for (JsonNode node : jsonNode) {
                ResExchgDTO resExchgDTO = convertJsonToExchangeDto(node);
                log.info("결과       :", resExchgDTO.getResult());
                log.info("통화코드    :", resExchgDTO.getCur_unit());
                log.info("국가/통화명 :", resExchgDTO.getCur_nm());
                log.info("송금받을때 :", resExchgDTO.getTtb());
                log.info("송금보낼때 :", resExchgDTO.getTts());
                log.info("매매 기준율 :", resExchgDTO.getDeal_bas_r());
                log.info("장부가격 :", resExchgDTO.getYy_efee_r());
                log.info("통화코드 :", resExchgDTO.getCur_unit());
                resExchgDTOS.add(resExchgDTO);
            }

            return resExchgDTOS;
        }

        return Collections.emptyList();
    }

    public ResExchgDTO convertJsonToExchangeDto(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.treeToValue(jsonNode, ResExchgDTO.class);
        } catch (JsonProcessingException e) {
            // 예외 처리 필요
            e.printStackTrace();
            return null;
        }
    }

    // 주말(토요일, 일요일)에는 환율정보가 오지 않음
    // -> 토요일, 일요일을 모두 금요일로 설정
    private String getSearchdate() {

        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        // 토요일
        if (dayOfWeek.getValue() == 6)
            return currentDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 일요일
        if (dayOfWeek.getValue() == 7)
            return currentDate.minusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

}
