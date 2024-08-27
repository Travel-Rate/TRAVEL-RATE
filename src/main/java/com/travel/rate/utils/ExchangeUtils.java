package com.travel.rate.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;

@Component
public class ExchangeUtils {
//    한국 수출입은행 API호출

//    API 인증키
    @Value("${exchange-authkey}")
    private String authkey;

    @Value("${exchange-data}")
    private String data;

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
                        .queryParam("data", data)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return parseJson(responseBody);
    }

    private JsonNode parseJson(String responseBody){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(responseBody);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
