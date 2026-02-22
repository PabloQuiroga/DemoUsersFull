package org.siar.application.usecase;

import lombok.RequiredArgsConstructor;
import org.siar.domain.model.User;
import org.siar.domain.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class GetUserUseCase {

    private final UserRepository userRepository;

    public Optional<User> execute(String username) {
        return userRepository.findByUsername(username);
    }
}
