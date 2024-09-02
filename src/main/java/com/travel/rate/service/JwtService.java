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
}
