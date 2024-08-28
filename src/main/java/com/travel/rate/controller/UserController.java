package com.travel.rate.controller;

import com.travel.rate.repository.TargetRateRepository;
import com.travel.rate.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//  의존성 주입
@RequiredArgsConstructor
@RestController
public class UserController {
    private final MemberService memberService;

//    ----------------------------------- 기준선

}
