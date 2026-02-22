package org.siar.domain.repository;

import org.siar.domain.model.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User save(User user);

    boolean deleteById(Long id);
}
