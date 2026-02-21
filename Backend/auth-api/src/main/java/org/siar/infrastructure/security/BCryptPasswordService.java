package org.siar.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;
import org.siar.domain.service.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ApplicationScoped
public class BCryptPasswordService implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    public BCryptPasswordService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String hash(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean verify(String password, String hash) {
        return passwordEncoder.matches(password, hash);
    }
}
