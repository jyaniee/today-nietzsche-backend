package com.jyanie.nietzsche.controller;

import com.jyanie.nietzsche.dto.LoginRequest;
import com.jyanie.nietzsche.dto.SignupRequest;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.UserRepository;
import com.jyanie.nietzsche.security.CustomUserDetails;
import com.jyanie.nietzsche.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        log.info("[로그인 요청 도착] email={}", loginRequest.getEmail());

        try {
            Map<String, String> result = authService.login(loginRequest);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        log.info("[회원가입 요청 도착] email = {}", signupRequest.getEmail());

        try {
            authService.signup(signupRequest);
            return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "서버 오류"));
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "인증되지 않은 사용자입니다."));
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        return ResponseEntity.ok(Map.of(
                "email", user.getEmail(),
                "name", user.getName()
        ));
    }
}
