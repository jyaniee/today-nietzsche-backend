package com.jyanie.nietzsche.service;

import com.jyanie.nietzsche.dto.LoginRequest;
import com.jyanie.nietzsche.dto.SignupRequest;
import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.UserRepository;
import com.jyanie.nietzsche.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public Map<String, String> login(LoginRequest request){
        System.out.println("🧪 passwordEncoder 클래스: " + passwordEncoder.getClass().getName());
        System.out.println("🔑 [로그인 요청 도착] email=" + request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("이메일이 존재하지 않습니다."));
        System.out.println("✅ 사용자 찾음: " + user.getEmail());
        System.out.println("✅ DB에 저장된 비밀번호 해시: " + user.getPassword());
        System.out.println("✅ 사용자가 입력한 비밀번호: " + request.getPassword());
        System.out.println("✅ matches 결과: " + passwordEncoder.matches(request.getPassword(), user.getPassword()));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw  new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getEmail());

        return Map.of(
                "token",token,
                "name", user.getName()
        );
    }

    public void signup(SignupRequest signupRequest) {
        System.out.println("🔐 [회원가입 요청 도착]");
        System.out.println("📨 입력된 이메일: " + signupRequest.getEmail());
        System.out.println("📨 입력된 이름: " + signupRequest.getName());
        System.out.println("📨 입력된 비밀번호: " + signupRequest.getPassword());

        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        System.out.println("🔒 비밀번호 암호화 완료");
        System.out.println("🔐 암호화된 비밀번호 해시: " + encodedPassword);

        User newUser = User.builder()
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .name(signupRequest.getName())
                .build();

        userRepository.save(newUser);
        System.out.println("✅ 회원가입 성공 - 저장된 유저: " + newUser.getEmail());
    }
}
