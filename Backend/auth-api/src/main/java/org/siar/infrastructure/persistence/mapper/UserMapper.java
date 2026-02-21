package org.siar.infrastructure.persistence.mapper;

import org.siar.domain.model.User;
import org.siar.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;
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

    public static UserEntity toEntity(User user) {
        if (user == null) return null;
        UserEntity entity = new UserEntity();
        entity.id = user.getId();
        entity.username = user.getUsername();
        entity.email = user.getEmail();
        entity.passwordHash = user.getPasswordHash();
        entity.status = user.getStatus();
        entity.failedAttempts = user.getFailedAttempts();
        entity.blockedUntil = user.getBlockedUntil();
        entity.lastLoginAt = user.getLastLoginAt();
        entity.lastLoginIp = user.getLastLoginIp();
        entity.createdAt = user.getCreatedAt();
        entity.updatedAt = user.getUpdatedAt();
        return entity;
    }
}
