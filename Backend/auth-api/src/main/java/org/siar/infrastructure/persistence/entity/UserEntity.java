package org.siar.infrastructure.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.siar.domain.model.UserStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Asume que el ID es autoincremental en la BD
    public Long id;

    @Column(name = "username", unique = true, nullable = false)
    public String username;

    @Column(name = "email", unique = true, nullable = false)
    public String email;

    @Column(name = "password_hash", nullable = false)
    public String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public UserStatus status;

    @Column(name = "failed_attempts", nullable = false)
    public int failedAttempts;

    @Column(name = "blocked_until")
    public LocalDateTime blockedUntil;

    @Column(name = "last_login_at")
    public LocalDateTime lastLoginAt;

    @Column(name = "last_login_ip")
    public String lastLoginIp;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;
}
