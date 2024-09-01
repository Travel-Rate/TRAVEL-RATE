package com.travel.rate.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendSimpleMail(Long memId, String email) {
        email="zpfhzpfh112@naver.com";
        String text = "목표 환율 알림 서비스에서 ";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("✈travel rate 회원님의 목표 환율 알림 메일입니다."); // 메일 제목
            mimeMessageHelper.setText(setContext(getTodayDate(), text), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            System.out.println("Succeeded to send Email");
        } catch (Exception e) {
            System.out.println("Failed to send Email");
            throw new RuntimeException(e);
        }
    }

    public String getTodayDate(){
        ZonedDateTime todayDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).atZone(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일");
        return todayDate.format(formatter);
    }

    //thymeleaf를 통한 html 적용
    public String setContext(String date, String text) {
        Context context = new Context();
        context.setVariable("date", date);
        context.setVariable("text", text);
        return templateEngine.process("todo", context);
    }

}
