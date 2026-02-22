package org.siar.domain.service;

import org.siar.domain.model.User;

public interface TokenService {
    String generateToken(User user);
}
