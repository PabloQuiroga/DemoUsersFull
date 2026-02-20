package org.siar.infrastructure.adapter.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.siar.domain.model.User;
import org.siar.domain.port.repository.UserRepository;
import org.siar.infrastructure.adapter.persistence.entity.UserEntity;
import org.siar.infrastructure.adapter.persistence.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PanacheUserRepository implements UserRepository {

    @Inject
    UserMapper userMapper;

    @Override
    @Transactional
    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        UserEntity entity;
        Optional<UserEntity> existing = UserEntity.findByIdOptional(user.getId());
        
        if (existing.isPresent()) {
            entity = existing.get();
            userMapper.updateEntityFromDomain(user, entity);
        } else {
            entity = userMapper.toEntity(user);
        }
        
        entity.persist();
    }

    @Override
    public Optional<User> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return UserEntity.<UserEntity>findByIdOptional(id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return UserEntity.<UserEntity>find("username", username)
                .firstResultOptional()
                .map(userMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return UserEntity.<UserEntity>listAll()
                .stream()
                .map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (id != null) {
            UserEntity.deleteById(id);
        }
    }
}
