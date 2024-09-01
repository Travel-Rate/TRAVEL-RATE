package com.travel.rate.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.rate.dto.res.ResExchgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Slf4j
public class ExchangeUtils {

//    API 인증키
    @Value("${exchange-authkey}")
    private String authkey;

    @Value("${exchange-data}")
    private String data;

    private final String searchdate = getSearchdate();

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
                        .queryParam("searchdate", searchdate)
                        .queryParam("data", data)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))     // 2초 뒤에 최대 3회 재시도
                        .filter(throwable -> throwable instanceof WebClientResponseException
                                || throwable instanceof java.net.SocketException) // 이 에러가 떴을때
                        .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) ->
                                retrySignal.failure()))
                )
                .doOnError(throwable ->
                        // 재 시도 실패 시 로그
                        System.out.println("재시도 실패: "+throwable.getMessage())
                )
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
                log.info("통화코드    :", resExchgDTO.getCur_unit());
                log.info("국가/통화명 :", resExchgDTO.getCur_nm());
                log.info("매매 기준율 :", resExchgDTO.getDeal_bas_r());
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

    public Map<String, Double> getExchgMap() {
        Map<String, Double> map = new HashMap<>();

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
                        .queryParam("searchdate", searchdate)
                        .queryParam("data", data)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode jsonNode = parseJson(responseBody);

        if (jsonNode != null && jsonNode.isArray()) {

            for (JsonNode node : jsonNode) {
                map.put(node.get("cur_unit").asText(), node.get("deal_bas_r").asDouble());
            }
        }

        return map;
    }
}
