package com.std.tothebook.security;

import com.std.tothebook.config.JwtTokenProvider;
import com.std.tothebook.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()

                .formLogin().disable()
                .httpBasic().disable()
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .antMatchers("/**")
                                .permitAll()
                )
				.headers()
				.frameOptions()
				.disable()
				.and()
				.logout((logout) -> logout
                        .logoutUrl("/api/sign-out")
                        .addLogoutHandler(new SecurityContextLogoutHandler())
                        .logoutSuccessHandler(((request, response, authentication) -> {}))
                )
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()) // 인증
                .accessDeniedHandler(new AccessDeniedHandlerImpl()) // 인가 (현재 인가 없음)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
