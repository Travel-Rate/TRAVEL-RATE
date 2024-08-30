package com.travel.rate.service;

import com.travel.rate.domain.Member;
import com.travel.rate.dto.req.ReqMemberDTO;
import com.travel.rate.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

//     ----------------------------------- 기준선

    // 아이디 중복확인
    public void getEmailFind(ReqMemberDTO reqMemberDTO)throws Exception{
        if(memberRepository.existsByEmail(reqMemberDTO.getEmail())){
            throw new DataIntegrityViolationException("이미 등록된 이메일입니다.");
        }
    }

    // 회원 가입
    public void setMemberAdd(ReqMemberDTO reqMemberDTO)throws Exception{
        Member member = Member.builder()
                .email(reqMemberDTO.getEmail())
                .name(reqMemberDTO.getName())
                .password(reqMemberDTO.getPassword())
                .build();
        memberRepository.save(member);
    }

}
