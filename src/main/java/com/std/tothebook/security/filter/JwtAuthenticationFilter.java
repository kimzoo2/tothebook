package com.std.tothebook.security.filter;

import com.std.tothebook.api.enums.AuthorizationType;
import com.std.tothebook.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    // Request 헤더에서 token 값 가져오기
    // Bearer 체크
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith(AuthorizationType.BEARER.getCode())) {
            return token.substring(7);
        }

        return null;
    }
}
