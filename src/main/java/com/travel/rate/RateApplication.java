package com.travel.rate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RateApplication {

    public static void main(String[] args) {
        // 스프링 애플리케이션을 실행하고 컨텍스트를 얻어옵니다.
        ConfigurableApplicationContext context =
                SpringApplication.run(RateApplication.class, args);
    }

}
