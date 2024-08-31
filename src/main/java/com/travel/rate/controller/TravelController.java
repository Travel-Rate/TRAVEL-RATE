package com.travel.rate.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("travel")
@Controller
public class TravelController {
    @PostMapping("countries")
    public ResponseEntity travel(RequestBody travelReqDto){
        // 여행지 3개 추천
        return ResponseEntity.ok().body("");
    }

    @GetMapping("{country_id}/cards")
    public ResponseEntity cards(@PathVariable Long id){
        // 나라에 해당하는 카드 필터링 3개 추천
        return ResponseEntity.ok().body("");
    }
}
