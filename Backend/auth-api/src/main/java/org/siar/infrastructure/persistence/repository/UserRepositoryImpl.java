package org.siar.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.siar.domain.model.User;
import org.siar.domain.repository.UserRepository;
import org.siar.infrastructure.persistence.entity.UserEntity;
import org.siar.infrastructure.persistence.mapper.UserMapper;

import java.util.Optional;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository, PanacheRepository<UserEntity> {

    @Override
    public Optional<User> findUserById(Long id) {
        return find("id", id).firstResultOptional().map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return find("username", username).firstResultOptional().map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional().map(UserMapper::toDomain);
    }

    @Override
    @Transactional
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        if (entity.id == null) {
            persist(entity);
        } else {
            // Si ya tiene ID, hacemos merge para actualizar
            entity = getEntityManager().merge(entity);
        }
        return UserMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return delete("id", id) > 0;
    }
}
