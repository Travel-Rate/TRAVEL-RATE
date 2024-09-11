package com.travel.rate.controller;

import com.travel.rate.domain.Member;
import com.travel.rate.dto.member.ReqLoginDTO;
import com.travel.rate.dto.member.ResLoginDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
    public ResApiResultDTO<ResLoginDTO> login(@RequestBody ReqLoginDTO reqLoginDTO) {

        return ResApiResultDTO.dataOk(jwtService.generateAccessToken(reqLoginDTO), "로그인 성공");
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("logout")
    public ResApiResultDTO<String> logout(@RequestHeader("Authorization") String authorizationHeader){
        jwtService.logout(authorizationHeader);
        return ResApiResultDTO.success(null, "로그아웃 성공");
    }


    @PreAuthorize("hasRole('USER') and (#memId.toString() == princial.username)")
    @PostMapping("test")
    public ResApiResultDTO<String> test(@AuthenticationPrincipal User user){
        log.info("토큰테스트 유저={}",user.getUsername());
        log.info("authorities = {}", user.getAuthorities());
        return ResApiResultDTO.success(user.getUsername(), "토큰 테스트");
    }

//    ----------------------------------- 기준선

    // 아이디 중복 확인
    @PostMapping("check")
    public ResApiResultDTO<String> getEmailFind(@RequestBody ReqMemberDTO reqMemberDTO){
        memberService.getEmailFind(reqMemberDTO);
        return ResApiResultDTO.success(null, ResponseCode.USER_EMAIL_SUCCESS.getMessage());
    }

    // 회원가입
    @PostMapping("create")
    public ResApiResultDTO<Object> setMemberAdd(@RequestBody ReqMemberDTO reqMemberDTO)throws Exception{
        memberService.setMemberAdd(reqMemberDTO);
        return ResApiResultDTO.success(null, ResponseCode.USER_ADD_SUCCESS.getMessage());
    }

}
