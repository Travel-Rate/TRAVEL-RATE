package com.travel.rate.controller;

import com.travel.rate.domain.Member;
import com.travel.rate.dto.member.ReqLoginDTO;
import com.travel.rate.dto.req.ReqMemberDTO;
import com.travel.rate.dto.res.ResApiResultDTO;
import com.travel.rate.dto.res.ResponseCode;
import com.travel.rate.service.CardService;
import com.travel.rate.service.JwtService;
import com.travel.rate.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//  의존성 주입
@RequiredArgsConstructor
@RestController
@RequestMapping("member/*")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody ReqLoginDTO reqLoginDTO) {
        return ResponseEntity.ok().body(jwtService.generateAccessToken(reqLoginDTO));
    }

    @PostMapping("logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String authorizationHeader){
        jwtService.logout(authorizationHeader);
        return ResponseEntity.ok().body("로그아웃");
    }

    @PostMapping("test")
    public ResponseEntity test(@RequestHeader("Authorization") String authorizationHeader){
        Map<String, Object> map = jwtService.validateTokenAndGetMember(authorizationHeader);
        return ResponseEntity.ok().body("로그인중인 유저 : "+map);
    }

//    ----------------------------------- 기준선

    // 아이디 중복 확인
    @PostMapping("check")
    public ResApiResultDTO<String> getEmailFind(@RequestBody ReqMemberDTO reqMemberDTO){
        memberService.getEmailFind(reqMemberDTO);
        return ResApiResultDTO.success(null,ResponseCode.USER_EMAILCHECK_SUCCESS.getMessage());
    }

    // 회원가입
    @PostMapping("create")
    public ResApiResultDTO<Object> setMemberAdd(@RequestBody ReqMemberDTO reqMemberDTO)throws Exception{
        memberService.setMemberAdd(reqMemberDTO);
        return ResApiResultDTO.success(null,ResponseCode.USER_ADD_SUCCESS.getMessage());
    }

}
