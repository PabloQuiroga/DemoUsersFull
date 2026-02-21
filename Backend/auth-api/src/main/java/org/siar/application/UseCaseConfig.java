package org.siar.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.siar.application.usecase.RegisterUserUseCase;
import org.siar.domain.repository.UserRepository;

@ApplicationScoped
public class UseCaseConfig {

    @Produces
    @ApplicationScoped
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository) {
        return new RegisterUserUseCase(userRepository);
    }
}
