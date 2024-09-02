package com.travel.rate.service;

import com.travel.rate.domain.Member;
import com.travel.rate.dto.member.ReqLoginDTO;
import com.travel.rate.repository.MemberRepository;
import com.travel.rate.utils.JWTUtill;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class JwtService {
    private final MemberRepository memberRepository;
    private final JWTUtill jwtUtill;

    public String generateAccessToken(ReqLoginDTO dto){
        String accessToken = "";
            Member entity = memberRepository.findByEmail(dto.getEmail());
            log.info("member = {}", entity.getEmail());
            log.info("member = {}", entity.getPassword());

            if (entity == null)  return null;
            if (!dto.getPassword().equals(entity.getPassword())) return null;
            accessToken = jwtUtill.generateToken(converToMap(entity), 7);

            entity.setAtk(accessToken);

            memberRepository.save(entity);

        return accessToken;
    }

    public Map<String, Object> converToMap(Member entity){
        Map<String, Object> map = new HashMap<>();
        map.put("email", entity.getEmail());
        map.put("name", entity.getName());
        map.put("mem_id", entity.getMemId());
        map.put("role", "ROLE_USER");
        return map;
    }

    public String logout(String accessToken){

        Member entity = memberRepository.findByAtk(accessToken);
        if(entity==null) return "이미 로그아웃된 유저";
        entity.setAtk(null);
        memberRepository.save(entity);
        return "로그아웃 완료";
    }

    public Map<String, Object> validateTokenAndGetMember(String accessToken){
        Member entity = memberRepository.findByAtk(accessToken);
        if(entity==null) { return null;
        }
        Map<String,Object> map = jwtUtill.validateToken(accessToken);
        return map;
    }
}
