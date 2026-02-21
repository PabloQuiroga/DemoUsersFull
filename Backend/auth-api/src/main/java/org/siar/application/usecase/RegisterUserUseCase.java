package org.siar.application.usecase;

import lombok.RequiredArgsConstructor;
import org.siar.domain.model.User;
import org.siar.domain.model.UserStatus;
import org.siar.domain.port.UserRepository;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    public User execute(String username, String email, String passwordHash) {

        userRepository.findByUsername(username)
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Username already exists");
                });

        userRepository.findByEmail(email)
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email already exists");
                });

        User user = User.builder()
                .username(username)
                .email(email)
                .passwordHash(passwordHash)
                .status(UserStatus.ACTIVE)
                .failedAttempts(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }
}
