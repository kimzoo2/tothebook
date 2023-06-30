package com.std.tothebook.config;

import com.std.tothebook.api.enums.AuthorizationType;
import com.std.tothebook.security.JsonWebToken;
import com.std.tothebook.security.SecurityUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    private final Key key;

    @Value("${jwt.expired-time}")
    private long expiredTime;

    @Value("${jwt.refresh-expired-time}")
    private long refreshExpiredTime;

    public JwtTokenProvider(@Value("${jwt.key}") String secretKey, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.key = Keys.hmacShaKeyFor(secretByteKey);
    }

    // TODO: USER_ROLE
    // 토큰 생성
    public JsonWebToken createToken(long id) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(id));

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiredTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JsonWebToken.builder()
                .grantType(AuthorizationType.BEARER.getCode())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getId(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰으로 회원 Id 추출
    public String getId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 유효성 체크
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            // TODO tokenService 사용해서 체크
            return !claims.getBody().getExpiration().before(new Date());
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("토큰 에러 (잘못된 토큰 구조): {}", e.toString());
        } catch (ExpiredJwtException e) {
            log.error("토큰 에러 (토큰 만료): {}", e.toString());
        } catch (UnsupportedJwtException e) {
            log.error("토큰 에러 (형식 상이): {}", e.toString());
        } catch (IllegalArgumentException e) {
            log.error("토큰 에러 (claim empty): {}", e.toString());
        }
        return false;
    }

    // 인증된 회원 정보 조회
    public SecurityUser getUser() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // 인증된 회원 id 조회
    public long getUserId() {
        return getUser().getId();
    }

}
