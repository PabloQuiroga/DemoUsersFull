package org.siar.application.usecase;

import lombok.RequiredArgsConstructor;
import org.siar.domain.exception.InvalidCredentialsException;
import org.siar.domain.exception.UserBlockedException;
import org.siar.domain.model.User;
import org.siar.domain.port.UserRepository;

@RequiredArgsConstructor
public class LoginUserUseCase {

    private final UserRepository userRepository;

    private final int maxAttempts = 5;
    private final int blockMinutes = 15;

    public User execute(String username, String passwordHash, String ip) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (user.isBlocked()) {
            throw new UserBlockedException("User is blocked");
        }

        if (!user.getPasswordHash().equals(passwordHash)) {
            user.registerFailedAttempt(maxAttempts, blockMinutes);
            userRepository.save(user);
            throw new InvalidCredentialsException("Invalid credentials");
        }

        user.registerSuccessfulLogin(ip);
        return userRepository.save(user);
    }
}
