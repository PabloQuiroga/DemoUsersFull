package org.siar.infrastructure.adapter.persistence.mapper;

import org.siar.domain.model.User;
import org.siar.infrastructure.adapter.persistence.entity.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.id);
        user.setUsername(entity.username);
        user.setEmail(entity.email);
        user.setPassword(entity.password); // Considerar si el password debe ser mapeado al dominio
        return user;
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.id = domain.getId();
        entity.username = domain.getUsername();
        entity.email = domain.getEmail();
        entity.password = domain.getPassword();
        return entity;
    }

    // Overload for updating an existing entity
    public void updateEntityFromDomain(User domain, UserEntity entity) {
        if (domain == null || entity == null) {
            return;
        }
        // ID should not be updated
        entity.username = domain.getUsername();
        entity.email = domain.getEmail();
        entity.password = domain.getPassword();
    }
}
