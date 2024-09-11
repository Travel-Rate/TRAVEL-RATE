package com.travel.rate.config;

import com.travel.rate.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("member/login", "member/check", "member/create", "exchange-rate/list").permitAll()
                                .requestMatchers("travel/*").permitAll()
                                .requestMatchers("exchange-rate/target/*", "member/logout", "member/test").authenticated()
                                .anyRequest().permitAll()  // 모든 요청에 대해 인증을 요구하지 않음
                )
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
                .headers(headers -> headers.disable())  // X-Frame-Options 비활성화 (필요한 경우)
                .formLogin(formLogin -> formLogin.disable())  // 기본 로그인 폼 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())  // HTTP Basic 인증 비활성화
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화
                .logout(logout -> logout.disable())
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
