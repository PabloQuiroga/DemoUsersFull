package org.siar.infrastructure.persistence.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.siar.domain.model.User;
import org.siar.domain.repository.UserRepository;
import org.siar.infrastructure.persistence.entity.UserEntity;
import org.siar.infrastructure.persistence.mapper.UserMapper;

import java.util.Optional;

@ApplicationScoped
@Transactional
public class UserRepositoryJpa implements UserRepository {

    @Inject
    EntityManager em;

    @Inject
    UserMapper userMapper;

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        UserEntity entity = em.find(UserEntity.class, id);
        return Optional.ofNullable(entity).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }
        return em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        UserEntity entity;
        if (user.getId() != null) {
            // Update existing
            entity = em.find(UserEntity.class, user.getId());
            if (entity != null) {
                userMapper.updateEntityFromDomain(user, entity);
                entity = em.merge(entity);
            } else {
                // ID provided but not found, treat as new or throw error depending on logic.
                // Here treating as new with ID (if DB allows) or letting DB generate.
                // Usually for auto-generated IDs, we just persist a new entity.
                entity = userMapper.toEntity(user);
                em.persist(entity);
            }
        } else {
            // Create new
            entity = userMapper.toEntity(user);
            em.persist(entity);
        }
        
        // Flush to get the generated ID if it was a new entity
        em.flush(); 
        
        return userMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            UserEntity entity = em.find(UserEntity.class, id);
            if (entity != null) {
                em.remove(entity);
            }
        }
    }
}
