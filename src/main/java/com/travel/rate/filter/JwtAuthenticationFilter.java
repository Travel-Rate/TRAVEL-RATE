package com.travel.rate.filter;

import com.travel.rate.domain.Member;
import com.travel.rate.utils.JWTUtill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtill jwtUtill;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = parseBearerToken(request);
        if(token!=null){
            if(jwtUtill.validateToken(token)!=null && jwtUtill.isTokenInDB(token)){
                User user = parseUserSpecification(token);
                AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, token, user.getAuthorities());
                authenticated.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticated);
            }else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    private User parseUserSpecification(String token) {
        Map<String, Object> map = jwtUtill.validateToken(token);
        String memId = map.get("memId").toString();
        log.info("memid = {}",memId);
        String role = (String) map.get("role");
        if (memId == null || memId.isEmpty()) {
            throw new IllegalArgumentException("Username is null or empty");
        }
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role is null or empty");
        }
        return new User(memId, "", List.of(new SimpleGrantedAuthority(role)));
    }
}
