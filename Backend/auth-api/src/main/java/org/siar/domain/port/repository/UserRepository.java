package org.siar.domain.port.repository;

import org.siar.domain.model.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository {

    void save(User user);

    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void deleteById(String id);
}
