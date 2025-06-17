package com.jyanie.nietzsche;

import com.jyanie.nietzsche.entity.User;
import com.jyanie.nietzsche.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class NietzscheApplication {

	public static void main(String[] args) {
		SpringApplication.run(NietzscheApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			String targetEmail = "test@example.com";

			userRepository.findByEmail(targetEmail).ifPresent(existing -> {
				userRepository.delete(existing);
				System.out.println("🗑 기존 유저 삭제됨: " + targetEmail);
			});

			String encoded = passwordEncoder.encode("1234");
			System.out.println("💾 bcrypt 해시: " + encoded);

			User user = User.builder()
					.email(targetEmail)
					.password(encoded)
					.name("니체")
					.build();

			userRepository.save(user);
			System.out.println("✅ 테스트 유저 삽입 완료");
		};
	}

}
