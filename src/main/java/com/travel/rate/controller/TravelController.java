package com.travel.rate.controller;

import com.travel.rate.dto.res.ResApiResultDTO;
import com.travel.rate.dto.travel.ReqTravelDTO;
import com.travel.rate.repository.CardRepository;
import com.travel.rate.service.CardService;
import com.travel.rate.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("travel")
@Controller
public class TravelController {
    private final TravelService travelService;
    private final CardRepository cardRepository;
    private final CardService cardService;

    @PostMapping("countries")
    public ResApiResultDTO travel(@RequestBody ReqTravelDTO reqTravelDTO){
        // 여행지 3개 추천
        return ResApiResultDTO.success(travelService.makeCountryRecommandation(reqTravelDTO), null);
    }

    @GetMapping("{country_id}/cards")
    public ResApiResultDTO cards(@PathVariable("country_id") Long ctrId){
        // 나라에 해당하는 카드 필터링 3개 추천
        return ResApiResultDTO.success(cardService.get3Cards(ctrId), null);
    }
}
