package com.write.reco.security;

import com.write.reco.dto.response.JwtResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private final Key key;

    public JwtProvider(Environment env) {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("jwt.token.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtResponse createToken(Authentication authentication) {

        long now = new Date().getTime();

        // Access Token 생성
        Date accessTokenExpires = new Date(now + 43200000);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(accessTokenExpires)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtResponse.create(accessToken);
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내기
    public Authentication getAuthentication(String accessToken) {

        // 토큰 복호화
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        /* 권한이 존재하지 않음  => null (x)
        의미없는 값이라도 Collections.EMPTY_LIST, Collections.singletonList(new SimpleGrantedAuthority(DEFAULT)) 설정하기
         */
        User principal = new User(claims.getSubject(), "", Collections.EMPTY_LIST);

        return new UsernamePasswordAuthenticationToken(principal, "", Collections.EMPTY_LIST);
    }

    public HttpStatus validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.OK;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}