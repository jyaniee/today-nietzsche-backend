package com.jyanie.nietzsche.security;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // csrf 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login").permitAll() // 로그인 경로 허용
                        .anyRequest().authenticated()  // 그외는 인증 필요
                )
                .addFilterBefore(new JwtFilter(jwtUtil, customUserDetailsService),
                                    UsernamePasswordAuthenticationFilter.class)
                .httpBasic(httpBasic -> httpBasic.disable()) // 팝업 로그인 비활성화
                .formLogin(form -> form.disable()); // 폼 로그인 비활성화
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
