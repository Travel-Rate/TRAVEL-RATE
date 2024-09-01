package com.travel.rate.controller;

import com.travel.rate.domain.Member;
import com.travel.rate.dto.req.ReqMemberDTO;
import com.travel.rate.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//  의존성 주입
@RequiredArgsConstructor
@RestController
@RequestMapping("member/*")
@Slf4j
public class MemberController {
    private final MemberService memberService;

//    ----------------------------------- 기준선

    // 아이디 중복 확인
    @PostMapping("check")
    public ResponseEntity<String> getEmailFind(@RequestBody ReqMemberDTO reqMemberDTO)throws Exception{
        try {
            memberService.getEmailFind(reqMemberDTO);
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.ok("이미 등록된 이메일입니다.");
        }
    }

    // 회원가입
    @PostMapping("create")
    public ResponseEntity<String> setMemberAdd(@RequestBody ReqMemberDTO reqMemberDTO)throws Exception{
        System.out.println(reqMemberDTO.toString());
        try {
            memberService.setMemberAdd(reqMemberDTO);
            return ResponseEntity.ok("회원가입을 성공했습니다.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }
    }

}
