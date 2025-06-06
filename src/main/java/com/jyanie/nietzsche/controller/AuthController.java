package com.jyanie.nietzsche.controller;

import com.jyanie.nietzsche.dto.LoginRequest;
import com.jyanie.nietzsche.dto.SignupRequest;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.UserRepository;
import com.jyanie.nietzsche.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest){
        log.info("[회원가입 요청 도착] email = {}", signupRequest.getEmail());

        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "이미 존재하는 이메일입니다."));
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User newUser = User.builder()
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .name(signupRequest.getName())
                .build();
        
        userRepository.save(newUser);
        
        return ResponseEntity.ok(Map.of("message", "회원가입 성공"));
    }
}
