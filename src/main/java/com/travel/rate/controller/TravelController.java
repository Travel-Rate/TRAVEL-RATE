package com.travel.rate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("travel")
@Controller
public class TravelController {
    @PostMapping
    public ResponseEntity travel(RequestBody travelReqDto){
        // 여행지 3개 추천 + 카드 3개씩
        return ResponseEntity.ok().body("");
    }
}
