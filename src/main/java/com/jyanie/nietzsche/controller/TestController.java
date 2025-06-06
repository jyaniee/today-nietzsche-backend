package com.jyanie.nietzsche.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/protected")
    public ResponseEntity<String> testProtectedEndpoint() {
        return ResponseEntity.ok("인증 성공! 보호된 엔드포인트 접근 허용됨.");
    }
}
