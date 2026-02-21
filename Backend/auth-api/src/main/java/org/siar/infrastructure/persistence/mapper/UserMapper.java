package org.siar.infrastructure.persistence.mapper;

import org.siar.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.siar.infrastructure.persistence.entity.UserEntity;

@ApplicationScoped
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.builder()
                .id(entity.id)
                .username(entity.username)
                .email(entity.email)
                .passwordHash(entity.passwordHash)
                .status(entity.status)
                .failedAttempts(entity.failedAttempts)
                .blockedUntil(entity.blockedUntil)
                .lastLoginAt(entity.lastLoginAt)
                .lastLoginIp(entity.lastLoginIp)
                .createdAt(entity.createdAt)
                .updatedAt(entity.updatedAt)
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.id = domain.getId();
        entity.username = domain.getUsername();
        entity.email = domain.getEmail();
        entity.passwordHash = domain.getPasswordHash();
        entity.status = domain.getStatus();
        entity.failedAttempts = domain.getFailedAttempts();
        entity.blockedUntil = domain.getBlockedUntil();
        entity.lastLoginAt = domain.getLastLoginAt();
        entity.lastLoginIp = domain.getLastLoginIp();
        entity.createdAt = domain.getCreatedAt();
        entity.updatedAt = domain.getUpdatedAt();
        return entity;
    }

    public void updateEntityFromDomain(User domain, UserEntity entity) {
        if (domain == null || entity == null) {
            return;
        }
        // ID y createdAt no se actualizan
        entity.username = domain.getUsername();
        entity.email = domain.getEmail();
        entity.passwordHash = domain.getPasswordHash();
        entity.status = domain.getStatus();
        entity.failedAttempts = domain.getFailedAttempts();
        entity.blockedUntil = domain.getBlockedUntil();
        entity.lastLoginAt = domain.getLastLoginAt();
        entity.lastLoginIp = domain.getLastLoginIp();
        entity.updatedAt = domain.getUpdatedAt();
    }
}
