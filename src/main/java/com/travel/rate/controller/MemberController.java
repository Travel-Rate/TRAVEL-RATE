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
    public ResApiResultDTO login(@RequestBody ReqLoginDTO reqLoginDTO) {
        return ResApiResultDTO.success(jwtService.generateAccessToken(reqLoginDTO), "로그인 성공");
    }

    @PostMapping("logout")
    public ResApiResultDTO logout(@RequestHeader("Authorization") String authorizationHeader){
        jwtService.logout(authorizationHeader);
        return ResApiResultDTO.success(null, "로그아웃 성공");
    }

    @PostMapping("test")
    public ResApiResultDTO test(@RequestHeader("Authorization") String authorizationHeader){
        Map<String, Object> map = jwtService.validateTokenAndGetMember(authorizationHeader);
        return ResApiResultDTO.success(map, "토큰 테스트");
    }

//    ----------------------------------- 기준선

    // 아이디 중복 확인
    @PostMapping("check")
    public ResApiResultDTO<String> getEmailFind(@RequestBody ReqMemberDTO reqMemberDTO){
        memberService.getEmailFind(reqMemberDTO);
        return ResApiResultDTO.success(ResponseCode.USER_ALREADY_EXIST.getMessage(),null);
    }

    // 회원가입
    @PostMapping("create")
    public ResApiResultDTO<Object> setMemberAdd(@RequestBody ReqMemberDTO reqMemberDTO)throws Exception{
        memberService.setMemberAdd(reqMemberDTO);
        return ResApiResultDTO.success(ResponseCode.INTERNAL_SERVER_ERROR.getMessage(),null);
    }

}