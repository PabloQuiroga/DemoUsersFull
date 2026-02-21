package org.siar.application.usecase;

import io.quarkus.elytron.security.common.BcryptUtil;
import lombok.RequiredArgsConstructor;
import org.siar.domain.model.User;
import org.siar.domain.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private static final int MAX_ATTEMPTS = 3;
    private static final int BLOCK_MINUTES = 15;

    public User execute(String username, String password, String ipAddress) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        User user = userOpt.get();

        if (user.isBlocked()) {
            throw new IllegalStateException("User is blocked. Try again later.");
        }

        if (!BcryptUtil.matches(password, user.getPasswordHash())) {
            user.registerFailedAttempt(MAX_ATTEMPTS, BLOCK_MINUTES);
            userRepository.save(user);
            throw new IllegalArgumentException("Invalid credentials");
        }

        user.registerSuccessfulLogin(ipAddress);
        return userRepository.save(user);
    }
}
