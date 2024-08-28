package com.travel.rate.controller;

import com.travel.rate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

//  의존성 주입
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

//    ----------------------------------- 기준선

}
